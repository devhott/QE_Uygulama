package utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ElementReader {
    private static final Logger logger = LogManager.getLogger(ElementReader.class);
    private static List<ElementInfo> elements;

    static {
        try {
            logger.info("📂 `page_elements.json` yükleniyor...");
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/test/resources/page_elements.json");

            if (!file.exists()) {
                throw new RuntimeException("⚠️ `page_elements.json` dosyası bulunamadı! Lütfen dosyanın yolunu kontrol edin.");
            }

            elements = objectMapper.readValue(file, new TypeReference<List<ElementInfo>>() {});
            logger.info("✅ `page_elements.json` başarıyla yüklendi.");
        } catch (IOException e) {
            logger.error("❌ `page_elements.json` yüklenirken hata oluştu!", e);
            throw new RuntimeException("JSON dosyası okunamadı: " + e.getMessage(), e);
        }
    }

    public static ElementInfo getElement(String key) {
        Optional<ElementInfo> element = elements.stream()
                .filter(e -> e.getKey().equalsIgnoreCase(key))
                .findFirst();

        if (element.isEmpty()) {
            logger.error("⚠️ JSON içinde '{}' anahtarı bulunamadı! Lütfen `page_elements.json` dosyasını kontrol edin.", key);
            throw new RuntimeException("❌ Element not found in JSON: " + key);
        }

        return element.get();
    }
}
