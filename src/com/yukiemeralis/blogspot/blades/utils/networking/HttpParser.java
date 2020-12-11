package com.yukiemeralis.blogspot.blades.utils.networking;

import java.util.HashMap;

public class HttpParser 
{
    protected HashMap<String, String> data = new HashMap<>();

    /**
     * For use in {@link HttpRequester#makeSkinRequest(String)}
     * @return The skin's data value
     */
    public String getValue()
    {
        return data.get("value");
    }

    /**
     * For use in {@link HttpRequester#makeSkinRequest(String)}
     * @return The skin's signature
     */
    public String getSignature()
    {
        return data.get("signature");
    }

    /**
     * For use in {@link HttpRequester#makeSkinRequest(String)}
     * @return The skin's UUID
     */
    public String getUUID()
    {
        return data.get("id");
    }

    /**
     * Gets a key retrieved from the parser
     * @param key
     * @return value of the key, null if no data is at the key
     */
    public String getKey(String key)
    {
        return data.get(key);
    }
}
