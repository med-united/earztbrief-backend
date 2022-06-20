package health.medunited.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

@ApplicationScoped
public class PdfService {

    public InputStream generatePdfFile(Optional<List<String>> datamatrices) throws IOException {
        PDDocument document = new PDDocument();
        PDPage firstPage = new PDPage();
        document.addPage(firstPage);

        PDFont font = PDType1Font.HELVETICA_BOLD;

        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(103, 700);
        contentStream.drawString("Rezeptanforderung:");
        contentStream.endText();
        // Positions for left bottom corner of the first image (beginning of page) on the first page
        float positionX = 100;
        float positionY = 562;
        for (String datamatrix : datamatrices.get()) {
            byte[] ba = java.util.Base64.getDecoder().decode(datamatrix);
            PDImageXObject datamatrixImage = PDImageXObject.createFromByteArray(document, ba, "datamatrixImage");
            double widthOfDatamatrix = datamatrixImage.getWidth();
            double heightOfDatamatrix = datamatrixImage.getHeight();
            double scale = 0.5;
            double newWidthForDatamatrix = widthOfDatamatrix * scale;
            double newHeightForDatamatrix = heightOfDatamatrix * scale;
            if (positionY > 0) {
                contentStream.drawImage(datamatrixImage, positionX, positionY, (float) newWidthForDatamatrix, (float) newHeightForDatamatrix);
                positionY -= 158;
            } else {
                contentStream.close();
                PDPage newPage = new PDPage();
                document.addPage(newPage);
                contentStream = new PDPageContentStream(document, newPage, PDPageContentStream.AppendMode.APPEND, true, true);
                // Positions for left bottom corner of the first image (beginning of page) on all the other pages
                positionX = 100;
                positionY = 562;
                contentStream.drawImage(datamatrixImage, positionX, positionY, (float) newWidthForDatamatrix, (float) newHeightForDatamatrix);
                positionY -= 158;
            }
        }
        contentStream.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
