package com.burstlinker.budget;

import java.util.UUID;

/**
 * Created by Alan Solitar on 2016/01/16.
 */
public class FileNameGenerator
{

    public String generateName()
    {
        UUID randID = UUID.randomUUID();
        String fileName = randID.toString();
        return fileName;
    }
}
