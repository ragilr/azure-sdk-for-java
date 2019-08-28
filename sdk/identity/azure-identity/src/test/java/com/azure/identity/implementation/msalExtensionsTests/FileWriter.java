// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.azure.identity.implementation.msalExtensionsTests;

import com.azure.identity.implementation.msal_extensions.CrossPlatLock;

import java.io.File;
import java.io.FileOutputStream;

public class FileWriter {

    public static void main(String[] args) throws Exception {
        File file;
        String lockfile;

        if (args.length == 3) {
            lockfile = args[1];
            file = new File(args[2]);
        } else {
            System.out.println("wrong number of args lol????");
            return;
        }
        CrossPlatLock lock = new CrossPlatLock(lockfile);

        try {
            lock.lock();

            if (!file.exists())
                file.createNewFile();
            FileOutputStream os = new FileOutputStream(file, true);

            os.write(("< " + args[0] + "\n").getBytes());
            Thread.sleep(1000);
            os.write(("> " + args[0] + "\n").getBytes());

            os.close();

        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }


    }
}
