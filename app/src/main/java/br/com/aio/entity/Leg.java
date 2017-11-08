package br.com.aio.entity;

/**
 * Created by elton on 07/11/2017.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Leg {
    public Distance distance;
    public Duration duration;
    public String end_address;
    public List<Step> steps;

    public Collection<Step> getStepList() {
        List<Step> result = new ArrayList<Step>();
        for (Step step : steps) {
            result.add(step);
        }
        return result;
    }
}