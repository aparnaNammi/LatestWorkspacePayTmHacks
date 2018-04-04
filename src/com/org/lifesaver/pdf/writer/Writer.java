package com.org.lifesaver.pdf.writer;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.org.lifesaver.IncidentBuilder;
import com.org.lifesaver.dto.IncidentInfo;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Gopi Yarasani on 31-03-2018.
 */
public class Writer {

    public static void main(String[] args) {

        IncidentInfo incidentInfo = IncidentBuilder.buildIncidentInfo();
        PdfDocument pdf = null;
        try {
            pdf = new PdfDocument(new PdfReader("C:\\PayTMWorkspace\\NPHackathon\\LifeSaviorApp\\WebContent\\pdfdocs\\AccidentIncidentReportTemplate.pdf"),
                    new PdfWriter("C:\\PayTMWorkspace\\NPHackathon\\LifeSaviorApp\\WebContent\\pdfdocs\\IncidentReport"+ incidentInfo.getInjuryInfo().getName() +".pdf"));

            PdfAcroForm acroForm = PdfAcroForm.getAcroForm(pdf, false);
            Map<String, PdfFormField> formFields = acroForm.getFormFields();
            IncidentBuilder.populateAcroFields(formFields, incidentInfo);
            for (String field1:formFields.keySet()) {
                formFields.get(field1).setReadOnly(true);
                //System.out.println(field1 + " : " + formFields.get(field1).getValueAsString());
            }
            pdf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
