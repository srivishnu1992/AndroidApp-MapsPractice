package com.example.srivi.mapspractice;

import java.util.ArrayList;

/**
 * Created by srivi on 26-03-2018.
 */

public class Path {
    ArrayList<points> points =new ArrayList<points>();

    @Override
    public String toString() {
        return "Path{" +
                "points=" + points +
                '}';
    }

    public ArrayList<Path.points> getPoints() {
        return points;
    }

    public class points{
        double latitude;
        double longitude;

        @Override
        public String toString() {
            return "Points{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
