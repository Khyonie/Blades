package com.yukiemeralis.blogspot.blades.customspecials;

import com.yukiemeralis.blogspot.blades.enums.Element;

public class SpecialData 
{
    private String name, description;
    private Element element;

    public SpecialData(String name, String description, Element element)
    {
        this.name = name;
        this.description = description;
        this.element = element;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public Element getElement()
    {
        return element;
    }
}
