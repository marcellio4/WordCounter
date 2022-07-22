package com.word.counter.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.word.counter.component.Word;

@Controller
public class UploadController {

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/";
        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(file.getOriginalFilename());
            Files.write(path, bytes);
            List<String> words =
                    Files.lines(Path.of(file.getOriginalFilename())).map(line -> line.split(" "))
                            .flatMap(Arrays::stream).collect(Collectors.toList());
            List<String> cleanWords = new ArrayList<>();
            words.forEach(w -> {
                String clean = w.replaceAll("[.*#;,:]", "");
                cleanWords.add(clean);
            });

            Word word = new Word();
            word.setCount(cleanWords.size());
            word.setWords(cleanWords);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            redirectAttributes.addFlashAttribute("count", word.getCount());
            redirectAttributes.addFlashAttribute("avarage", word.calculateAvarageLength());
            redirectAttributes.addFlashAttribute("wordsOfLength", word.findWordsOfLength());
            redirectAttributes.addFlashAttribute("mostOccuringWordLength",
                    word.mostOccuringWordLength());
            redirectAttributes.addFlashAttribute("forWordLengths",
                    word.mostOccuringLengthforWords());
            // Delete a file once we all done
            Files.deleteIfExists(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String displayResult() {
        return "uploadForm";
    }

}
