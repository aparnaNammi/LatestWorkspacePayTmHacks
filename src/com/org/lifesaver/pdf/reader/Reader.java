package com.org.lifesaver.pdf.reader;

import java.io.IOException;
import java.util.Map;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.org.lifesaver.IncidentBuilder;
import com.org.lifesaver.dto.IncidentInfo;

/**
 * Created by Gopi Yarasani on 31-03-2018.
 */
public class Reader {



    public static void main(String[] args) {
        PdfDocument pdf = null;
        try {
            pdf = new PdfDocument(new PdfReader("C:\\PayTMWorkspace\\NPHackathon\\LifeSaviorApp\\WebContent\\pdfdocs\\IncidentReport.pdf"));

            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdf, false);
            Map<String, PdfFormField> formFields = acroForm.getFormFields();
            IncidentInfo incidentInfo = IncidentBuilder.buildFromPdf(formFields);
            System.out.println("Incident Date: " + incidentInfo.getDate());
            pdf.close();

            /*String text = PdfTextExtractor.getTextFromPage(pdf.getPage(1), new LocationTextExtractionStrategy());
            System.out.println("Extracted text:");
            System.out.println(text);*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
