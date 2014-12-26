/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package topcoder.srm.srm1_div1;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Given a picture composed entirely of horizontal and vertical line segments,
 * calculate the minimum number of times you must lift your pen to trace every
 * line segment in the picture exactly n times.
 *
 * Each line segment will be of the form "<x1> <y1> <x2> <y2>" (quotes for
 * clarity), representing a segment from (x1,y1) to (x2,y2). Segments may cross
 * each other. Segments may also overlap, in which case you should count the
 * overlapping region as appearing in the drawing only once. For example, say
 * the drawing were composed of two lines: one from (6,4) to (9,4), and one from
 * (8,4) to (14,4). Even though they overlap from (8,4) to (9,4), you should
 * treat the drawing as if it were a single line from (6,4) to (14,4). You would
 * not need to lift your pen at all to trace this drawing.
 *
 * Constraints - segments will contain between 1 and 50 elements, inclusive.
 *
 * Each element of segments will contain between 7 and 50 characters, inclusive.
 *
 * Each element of segments will be in the format "<X1>_<Y1>_<X2>_<Y2>" (quotes
 * for clarity). The underscore character represents exactly one space. The
 * string will have no leading or trailing spaces.
 *
 * <X1>, <Y1>, <X2>, and <Y2> will each be between -1000000 and 1000000,
 * inclusive, with no unnecessary leading zeroes.
 *
 * Each element of segments will represent a horizontal or vertical line
 * segment. No line segment will reduce to a point. -	n will be between 1 and
 * 1000000, inclusive.
 *
 * @author prakbans
 */
public class PenLift {

    /**
     * challenges: A. to make a graph for line-segments 1. line-segments could
     * be overlapping (then it should be merged into one) 2. keep track of valid
     * end points, later on they will become vertices {keep and maintain
     * vertex-set} 3. given a line-segment - know if its overlapping collinearly
     * if it is update the end points to new end-points in vertex-set 4. given a
     * line-segment - know if any of its end point lies on the existing
     * line-segment - create new vertices if it lies
     *
     * B. HOOH, we have a Graph(V, e) 1. This could have many connected
     * components, k > 1 2. For each connected component - DFS(?DFS not required
     * for degree but do required for connected components and their degrees) to
     * find the degree of each vertex start from any odd vertex degree
     *
     *
     */
    private static class LineSegment {

        private final Point from, to;

        LineSegment(Point from, Point to) {
            this.from = from;
            this.to = to;
        }

        boolean isEndpoint(Point point) {
            return point.equals(from) || point.equals(to);
        }

        // TODO: move here
        // TODO: true/false isLie(Point point)        
        // TODO: Point intersectingPoint or null: isIntersecting(LineSegment lineSeg)
        // TODO: LineSegment theonelinesegment or null ::isCollinearAndOverlappingOrOvermapping(LineSegment lineSeg)

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof LineSegment) {
                LineSegment ls = (LineSegment) obj;
                return (from.equals(ls.from)) && (to.equals(ls.to));
            }
            return super.equals(obj);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 67 * hash + Objects.hashCode(this.from);
            hash = 67 * hash + Objects.hashCode(this.to);
            return hash;
        }
    }

    private static class LineSegmentUtility {

        void normalizeLineSegments() {
            // TODO: This should take care of collinear merging of line-segments
            // TODO: If the any Point in line segment lies perpendicularly on another line segment 
            //          we need to divide the base line segments in to two.        

            Iterator<LineSegment> candidates = lineSegments.iterator();
            while (candidates.hasNext()) {
                LineSegment candidate = candidates.next();
                for (LineSegment against : lineSegments) {
                    if (!candidate.equals(against)) {
                        LineSegment normalizedForCollinearity = normalizeCollinearlyBounded(candidate, against);
                        if (normalizedForCollinearity != null) {
                            // This should take care of collinear merging of line-segments
                            lineSegments.removeAll(Arrays.asList(candidate, against));
                            lineSegments.add(normalizedForCollinearity);
                            // candidate modified
                            candidate = normalizedForCollinearity;
                        }
                    }
                }
            }

            candidates = lineSegments.iterator();
            while (candidates.hasNext()) {
                LineSegment candidate = candidates.next();
                for (LineSegment against : lineSegments) {
                    if (!candidate.equals(against)) {
                        // If the any Point in line segment lies perpendicularly on another line segment 
                        //          we need to divide the base line segments in to two.   
                        Point breakingPoint = lyingPoint(candidate.from, against);
                        breakingPoint = (breakingPoint == null) ? lyingPoint(candidate.to, against) : null;

                        // break against
                        if (breakingPoint != null && !breakingPoint.equals(against.from) && !breakingPoint.equals(against.to)) {
                            lineSegments.remove(against);
                            lineSegments.addAll(Arrays.asList(new LineSegment(against.from, breakingPoint), new LineSegment(breakingPoint, against.to)));
                        }
                    }
                }
            }
               
            // TODO: Check for intersections -point where two line segments intersect
            // http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
            
            // gather intersecting point and break the 2 line segments into 4
            
            
        }

        // converts 2 LineSegments into one, if those are aligned collinearly
        private LineSegment normalizeCollinearlyBounded(LineSegment segmentA, LineSegment segmentB) {
            LineSegment lineSegment = null;
            Orientation orientation = isCollinearlyOverlapping(segmentA, segmentB);
            if (orientation != null) {
                if (Orientation.HORIZONTAL == orientation) {
                    // x-varies, y-stays
                    lineSegment = new LineSegment(
                            new Point(min(segmentA.from.x, segmentA.to.x, segmentB.from.x, segmentB.to.x), segmentA.from.y),
                            new Point(max(segmentA.from.x, segmentA.to.x, segmentB.from.x, segmentB.to.x), segmentA.from.y));
                } else {
                    // x-stays, y-varies
                    lineSegment = new LineSegment(
                            new Point(segmentA.from.x, min(segmentA.from.y, segmentA.to.y, segmentB.from.y, segmentB.to.y)),
                            new Point(segmentA.from.x, max(segmentA.from.y, segmentA.to.y, segmentB.from.y, segmentB.to.y)));
                }
            }
            return lineSegment;
        }

        private int min(int... vals) {
            int min = vals[0];
            for (int val : vals) {
                if (val < min) {
                    min = val;
                }
            }
            return min;
        }

        private int max(int... vals) {
            int max = vals[0];
            for (int val : vals) {
                if (val > max) {
                    max = val;
                }
            }
            return max;
        }

        private Point lyingPoint(Point point, LineSegment segment) {
            if (point.x == segment.from.x && point.x == segment.to.x) {
                // TODO: since line segment's from should not necessarily start from low point - improve this so that end point doesn't matters
                //TODO: move a method to line segment if point lie on it
                if (segment.from.y <= point.y && point.y <= segment.to.y || segment.from.y >= point.y && point.y >= segment.to.y) {
                    return point;
                }
            } else if (point.y == segment.from.y && point.y == segment.to.y) {
                if (segment.from.x <= point.x && point.x <= segment.to.x || segment.from.x >= point.x && point.x >= segment.to.x) {
                    return point;
                }
            }
            return null;
        }

        private Orientation isCollinearlyOverlapping(LineSegment segmentA, LineSegment segmentB) {
            if (segmentA.from.x == segmentA.to.x && segmentA.to.x == segmentB.from.x && segmentB.from.x == segmentB.to.x) {
                if (segmentA.to.y <= segmentB.from.y || segmentB.to.y <= segmentA.from.y || 
                        (segmentA.from.y <= segmentB.from.y && segmentA.to.y <= segmentB.from.y && segmentA.from.y >= segmentB.to.y && segmentA.to.y >= segmentB.to.y) || 
                        (segmentB.from.y <= segmentA.from.y && segmentB.to.y <= segmentA.from.y && segmentB.from.y >= segmentA.to.y && segmentB.to.y >= segmentA.to.y) ||
                        
                        (segmentA.from.y <= segmentB.to.y && segmentA.to.y <= segmentB.to.y && segmentA.from.y >= segmentB.from.y && segmentA.to.y >= segmentB.from.y) || 
                        (segmentB.from.y <= segmentA.to.y && segmentB.to.y <= segmentA.to.y && segmentB.from.y >= segmentA.from.y && segmentB.to.y >= segmentA.from.y)) {
                    return Orientation.VERTICAL;
                }
            } else if (segmentA.from.y == segmentA.to.y && segmentA.to.y == segmentB.from.y && segmentB.from.y == segmentB.to.y) {
                if (segmentA.to.x <= segmentB.from.x || segmentB.to.x <= segmentA.from.x) {
                    return Orientation.HORIZONTAL;
                }
            }
            return null;
        }

        private enum Orientation {

            HORIZONTAL,
            VERTICAL;
        }
    }

    private static final Pattern COORDINATE_PATTERN = Pattern.compile("-?\\d+");
    
    // TODO: make it internal to numTimes and pass it to normalize and let it return normalized
    private static final Map<LineSegment, Boolean> weakMap = new ConcurrentHashMap<LineSegment, Boolean>();    
    private static final Set<LineSegment> lineSegments = Collections.newSetFromMap(weakMap);

    public int numTimes(String[] segments, int n) {
        for (String segment : segments) {
            Matcher match = COORDINATE_PATTERN.matcher(segment);
            // FIXME: Fix this - understand matches
            // if (!match.matches()) {
            //    throw new IllegalArgumentException(segment);
            // }
            int coordinates[] = new int[4], i = 0;
            while (match.find()) {
                coordinates[i++] = Integer.parseInt(match.group(0));
            }

            lineSegments.add(new LineSegment(new Point(coordinates[0], coordinates[1]), new Point(coordinates[2], coordinates[3])));
        }
        
        // TODO: LSNormalizer make class - do 3 things - colliner + lie + intersect
        // lie logic could go: intersect would cover that
        
        new LineSegmentUtility().normalizeLineSegments();

        // make graph
        Graph graph = new Graph(lineSegments);

        Map<Integer, Set<Point>> subGraphs = graph.getConnectedComponents();
        int numTimes = subGraphs.size() - 1;

        for (Map.Entry<Integer, Set<Point>> component : subGraphs.entrySet()) {
            int oddDegreeVertices = 0;
            for (Point vertex : component.getValue()) {
                if ((graph.getDegree(vertex) * n) % 2 != 0) {
                    oddDegreeVertices++;
                }
            }
            // line-segment cannot be converged into a point so isolated one-degee vertex not possible
            numTimes += (oddDegreeVertices == 0) ? 0 : oddDegreeVertices / 2 - 1;
        }

        return numTimes;
    }

    private class Graph {
        private final Map<Point, Set<Point>> graph = new HashMap<Point, Set<Point>>();

        private int componentCount = 0;
        Map<Point, Boolean> marked = new HashMap<Point, Boolean>();
        Map<Integer, Set<Point>> componentMap = new HashMap<Integer, Set<Point>>();
        
        Graph(Set<LineSegment> lineSegments) {
            for (LineSegment lineSegment : lineSegments) {
                Set<Point> fromAdj = graph.get(lineSegment.from);
                Set<Point> toAdj = graph.get(lineSegment.to);

                if (fromAdj == null) {
                    fromAdj = new HashSet<Point>();
                }
                if (toAdj == null) {
                    toAdj = new HashSet<Point>();
                }

                fromAdj.add(lineSegment.to);
                toAdj.add(lineSegment.from);

                graph.put(lineSegment.from, fromAdj);
                graph.put(lineSegment.to, toAdj);
            }
        }

        int getDegree(Point vertex) {
            Set<Point> adj = graph.get(vertex);
            if (adj == null) {
                throw new IllegalStateException("Vertex for degree-lookup is not is graph!");
            }
            return graph.get(vertex).size();
        }
        
        Map<Integer, Set<Point>> getConnectedComponents() {            
            Set<Point> vertices = this.graph.keySet();
            for (Point vertex : vertices) {
                marked.put(vertex, Boolean.FALSE);
            }

            for (Point vertex : vertices) {
                if (!marked.get(vertex)) {
                    dfs(vertex);
                    this.componentCount++;
                }
            }
            
            return componentMap;
        }

        private void dfs(Point vertex) {
            marked.put(vertex, Boolean.TRUE);

            Set<Point> idSet = componentMap.get(this.componentCount);
            if (idSet == null) {
                idSet = new HashSet<Point>();
            }
            idSet.add(vertex);
            componentMap.put(this.componentCount, idSet);

            for (Point adjPoint : this.graph.get(vertex)) {
                if (!marked.get(adjPoint)) {
                    dfs(adjPoint);
                }
            }
        }
    }
}
