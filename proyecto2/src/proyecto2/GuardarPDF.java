/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author André
 */
public class GuardarPDF { //clase para guardar pdf (no funciona)
    public static File generarPDFVendedoresEnEscritorio() throws IOException {
        String userHome = System.getProperty("user.home");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File out = new File(userHome + File.separator + "Desktop", "Vendedores_" + timestamp + ".pdf");
        String contenido = construirTextoVendedores();
        escribirPDFSimple(out, "Reporte de Vendedores", contenido);
        return out;
    }

    public static File generarPDFProductosEnEscritorio() throws IOException {
        String userHome = System.getProperty("user.home");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File out = new File(userHome + File.separator + "Desktop", "Productos_" + timestamp + ".pdf");
        String contenido = construirTextoProductos();
        escribirPDFSimple(out, "Reporte de Productos", contenido);
        return out;
    }

    private static String construirTextoVendedores() {
        StringBuilder sb = new StringBuilder();
        int n = GestorVendedores.getTotal();
        String[] c = GestorVendedores.getCodigos();
        String[] nms = GestorVendedores.getNombres();
        String[] g = GestorVendedores.getGeneros();
        int[] v = GestorVendedores.getVentasConfirmadas();
        sb.append("CODIGO | NOMBRE | GENERO | VENTAS\n");
        for (int i=0; i<n; i++) {
            sb.append(c[i]).append(" | ").append(nms[i]).append(" | ")
              .append(g[i]).append(" | ").append(v[i]).append("\n");
        }
        return sb.toString();
    }

    private static String construirTextoProductos() {
        StringBuilder sb = new StringBuilder();
        int n = GestorProductos.getTotal();
        String[] c = GestorProductos.getCodigos();
        String[] nms = GestorProductos.getNombres();
        String[] cat = GestorProductos.getCategorias();
        String[] atr = GestorProductos.getAtributoUnico();
        sb.append("CODIGO | NOMBRE | CATEGORIA | ATRIBUTO\n");
        for (int i=0; i<n; i++) {
            sb.append(c[i]).append(" | ").append(nms[i]).append(" | ")
              .append(cat[i]).append(" | ").append(atr[i]).append("\n");
        }
        return sb.toString();
    }

    // Escribe un PDF unipágina muy simple con texto monoespaciado
    private static void escribirPDFSimple(File archivo, String titulo, String cuerpo) throws IOException {
        // PDF minimalista
        String header = "%PDF-1.4\n";
        String fontObj = "1 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Courier >>\nendobj\n";
        // stream de contenido (texto)
        String content = "BT /F1 12 Tf 50 750 Td (" + escaparPDF(titulo) + ") Tj T* ("
                + escaparPDF(cuerpo).replace("\n", ") Tj T* (") + ") Tj ET";
        byte[] contentBytes = content.getBytes(StandardCharsets.US_ASCII);
        String lengthStr = String.valueOf(contentBytes.length);

        String streamObj = "2 0 obj\n<< /Length " + lengthStr + " >>\nstream\n" + content + "\nendstream\nendobj\n";
        String pageObj = "3 0 obj\n<< /Type /Page /Parent 4 0 R /Resources << /Font << /F1 1 0 R >> >> /Contents 2 0 R /MediaBox [0 0 612 792] >>\nendobj\n";
        String pagesObj = "4 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n";
        String catalogObj = "5 0 obj\n<< /Type /Catalog /Pages 4 0 R >>\nendobj\n";

        // xref
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(header.getBytes(StandardCharsets.US_ASCII));
        int x0 = header.length();

        int x1 = x0; baos.write(fontObj.getBytes(StandardCharsets.US_ASCII)); x1 = baos.size();
        int off1 = x0;

        int off2 = x1; baos.write(streamObj.getBytes(StandardCharsets.US_ASCII)); int x2 = baos.size();

        int off3 = x2; baos.write(pageObj.getBytes(StandardCharsets.US_ASCII)); int x3 = baos.size();

        int off4 = x3; baos.write(pagesObj.getBytes(StandardCharsets.US_ASCII)); int x4 = baos.size();

        int off5 = x4; baos.write(catalogObj.getBytes(StandardCharsets.US_ASCII)); int x5 = baos.size();

        String xref = "xref\n0 6\n0000000000 65535 f \n" +
                pad(off1) + " 00000 n \n" +
                pad(off2) + " 00000 n \n" +
                pad(off3) + " 00000 n \n" +
                pad(off4) + " 00000 n \n" +
                pad(off5) + " 00000 n \n";

        String trailer = "trailer\n<< /Size 6 /Root 5 0 R >>\nstartxref\n" + x5 + "\n%%EOF";

        baos.write(xref.getBytes(StandardCharsets.US_ASCII));
        baos.write(trailer.getBytes(StandardCharsets.US_ASCII));

        try (FileOutputStream fos = new FileOutputStream(archivo)) {
            fos.write(baos.toByteArray());
        }
    }

    private static String pad(int offset) {
        String s = String.valueOf(offset);
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<10 - s.length(); i++) sb.append('0');
        sb.append(s).append(' ');
        return sb.toString();
    }

    // Escapa paréntesis y barras para PDF
    private static String escaparPDF(String s) {
        return s.replace("\\","\\\\").replace("(","\\(").replace(")","\\)");
    }
}
