package sample.Model;

import java.io.Serializable;

public class Age implements Serializable
{
    private static final long serialVersionUID = 1;

    private int days;
    private int months;
    private int years;

    private Age()
    {
        //Prevent default constructor
    }

    public Age(int days, int months, int years)
    {
        this.days = days;
        this.months = months;
        this.years = years;
    }

    public int getDays()
    {
        return this.days;
    }

    public int getMonths()
    {
        return this.months;
    }

    public int getYears()
    {
        return this.years;
    }

    @Override
    public String toString()
    {
        return days + "/" + months + "/" + years;
    }
}