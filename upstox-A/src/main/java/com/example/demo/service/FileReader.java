package com.example.demo.service;

import com.google.common.base.Charsets;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileReader {
    public List<String> readFile(File jsonFile) throws IOException {
        return com.google.common.io.Files
                .readLines(jsonFile, Charsets.UTF_8);
    }
}
