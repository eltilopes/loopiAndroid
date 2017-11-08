package br.com.aio.entity;

/**
 * Created by elton on 07/11/2017.
 */

import java.util.ArrayList;
import java.util.List;

public class Route {
    public List<Leg> legs;
    public String summary;
    public List<Step> getAllSteps() {
        List<Step> result = new ArrayList<Step>();
        for (Leg leg : legs) {
            result.addAll(leg.getStepList());
        }
        return result;
    }
}