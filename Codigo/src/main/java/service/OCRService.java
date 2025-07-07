package service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import config.VisionClientFactory;

import java.io.FileInputStream;
import java.util.Collections;

public class OCRService {
    public static String extractText(String imagePath) throws Exception {
        try (ImageAnnotatorClient client = VisionClientFactory.createClientFromJson()) {
            ByteString imgBytes = ByteString.readFrom(new FileInputStream(imagePath));
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.DOCUMENT_TEXT_DETECTION).build();

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(Collections.singletonList(request));
            return response.getResponses(0).getFullTextAnnotation().getText();
        }
    }
}
