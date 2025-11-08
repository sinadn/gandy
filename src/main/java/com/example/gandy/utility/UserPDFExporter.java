package com.example.gandy.utility;

import com.example.gandy.entity.Address;
import com.example.gandy.entity.Cart;
import com.example.gandy.payload.response.FactorResponseForAdmin;
import com.github.mfathi91.time.PersianDate;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class UserPDFExporter {
    private Font iran_yekan_light = null;

    private Font iran_yekan_bold = null;

    private  FactorResponseForAdmin factorResponseForAdmin = null;

    private Address address = null;

    long sumMainPrice = 0;
    long sumDiscount = 0;

    public UserPDFExporter(FactorResponseForAdmin factorResponseForAdmin , Address address) throws IOException {

        ClassPathResource fontResource = new ClassPathResource("fonts/iran_yekan_regular.ttf");
        InputStream fontStream = fontResource.getInputStream();
        BaseFont customBaseFont = BaseFont.createFont("iran_yekan_regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontStream.readAllBytes(), null);
        Font customFont = new Font(customBaseFont, 12);


        ClassPathResource fontResource2 = new ClassPathResource("fonts/iran_yekan_bold.ttf");
        InputStream fontStream2 = fontResource2.getInputStream();
        BaseFont customBaseFont2 = BaseFont.createFont("iran_yekan_bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontStream2.readAllBytes(), null);
        Font customFont2 = new Font(customBaseFont2, 20);

        this.iran_yekan_light = customFont;
        this.iran_yekan_bold = customFont2;

        this.address = address;
        this.factorResponseForAdmin = factorResponseForAdmin;


    }

    private void writeTableHeader(PdfPTable table) throws IOException {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);


        cell.setPhrase(new Phrase("مبلغ کل پس از تخفیف (ریال)", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("تخفیف (ریال)", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("مبلغ کل (ریال)", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("مبلغ واحد(ریال)", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("تعداد", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("شرح کالا (مشخصات سفارش)", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("کد کالا", iran_yekan_light));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ردیف", iran_yekan_light));
        table.addCell(cell);

    }


    private void writeTableData(PdfPTable table, int tableNum) throws IOException {

        if (tableNum == 1) {
            for (int col = 1; col <= 5; col++) {
                if (col == 2 || col == 3) {
                    if (col == 2)
                        table.addCell(getCell(1, breakLine("نحوه ارسال : ", factorResponseForAdmin.getCartList().get(0).getFactor().getShipment()), 80, 0, 0, 200, Element.ALIGN_CENTER , 0));
                    if (col == 3)
                        table.addCell(getCell(1, breakLine("پرداخت : ", "خرید آنلاین"), 80, 0, 0, 200, Element.ALIGN_CENTER , 0));
                } else {
                    if (col == 1) {
                        table.addCell(getCell(0, breakLine("شماره فاکتور : ", String.valueOf(factorResponseForAdmin.getCartList().get(0).getFactor().getId())), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));
                    } else if (col == 4) {
                        table.addCell(getCell(0, breakLine("فروشنده : " + "فروشگاه موبایل گاندی", "نشانی : " + "گلستان ، گرگان ، نبش دادگاه انقلاب ، فروشگاه موبایل گاندی"), 30, 10, 10, 50, Element.ALIGN_LEFT , 0));
                    } else {
                        table.addCell(getCell(0, "فروشنده", 40, 0, 0, 50, Element.ALIGN_CENTER , 0));
                    }

                }
            }
            table.addCell(getCell(0, breakLine("شماره پیگیری : ", factorResponseForAdmin.getCartList().get(0).getFactor().getPayment().getSaleReferenceId()), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));
            table.addCell(getCell(0, stringBuild(factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getName() +"  "+ factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getFamily(), factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getNationalCode(), factorResponseForAdmin.getCartList().get(0).getFactor().getAddress(), factorResponseForAdmin.getCartList().get(0).getFactor().getUsers().getMobile(), "-", address.getPostalCode()), 10, 10, 10, 50, Element.ALIGN_LEFT , 0));
            table.addCell(getCell(0, "خریدار", 40, 0, 0, 50, Element.ALIGN_CENTER , 0));

        } else if (tableNum == 2) {
            int num = 0;
            for (Cart cart:factorResponseForAdmin.getCartList()) {
                sumMainPrice += cart.getFactorMainPrice() * cart.getCount();
                sumDiscount += cart.getFactorDiscountVal() * cart.getCount();

                num++;
                for (int j = 0; j < 8; j++) {
                    if (j == 0) {
                        table.addCell(getCell(0, priceFormat(addZeroToPrice((cart.getFactorMainPrice() * cart.getCount()) - (cart.getFactorDiscountVal() * cart.getCount()))), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));

                    } else if (j == 1) {
                        table.addCell(getCell(0, priceFormat(addZeroToPrice(cart.getFactorDiscountVal())), 10, 10, 0, 50, Element.ALIGN_CENTER,0));

                    } else if (j == 2) {
                        table.addCell(getCell(0, priceFormat(addZeroToPrice(cart.getFactorMainPrice()* cart.getCount())), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));

                    } else if (j == 3) {
                        table.addCell(getCell(0, priceFormat(addZeroToPrice(cart.getFactorMainPrice())), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));

                    } else if (j == 4) {
                        table.addCell(getCell(0, String.valueOf(cart.getCount()), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));

                    } else if (j == 5) {
                        table.addCell(getCell(0, cart.getProductCount().getProduct().getName() + "(" + cart.getProductCount().getColor() + ")", 10, 10, 10, 50, Element.ALIGN_LEFT , 0));

                    } else if (j == 6) {
                        table.addCell(getCell(0, String.valueOf(cart.getProductCount().getProduct().getId()), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));

                    } else if (j == 7) {
                        table.addCell(getCell(0, String.valueOf(num), 10, 10, 0, 50, Element.ALIGN_CENTER , 0));
                    }
                }
            }

        } else if (tableNum == 3) {
            for (int col = 1; col <= 2; col++) {
                if (col == 2) {
                    table.addCell(getCell(0, "", 0, 0, 0, 200, Element.ALIGN_BOTTOM , 0));
                } else {
                    PdfPCell pdfCell = new PdfPCell();
                    // Create the nested table with 2 columns
                    PdfPTable nestedTable = new PdfPTable(2);




                        // Add cells to the nested table
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(sumMainPrice)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "جمع خرید", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(sumDiscount)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "جمع تخفیف محصول", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(0)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "تخفیف سبد خرید", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(0)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "مبلغ کل تخفیف", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(factorResponseForAdmin.getShipment())), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "هزینه ارسال", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(0)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "جمع بسته بندی", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(0)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "جمع مالیات", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));
                    nestedTable.addCell(getCell(0, priceFormat(addZeroToPrice(0)), 0, 0, 0, 20, Element.ALIGN_RIGHT , 1));
                    nestedTable.addCell(getCell(0, "جمع بیمه ", 0, 0, 0, 20, Element.ALIGN_LEFT , 1));

                    // Add the nested table to the PdfPCell
                    pdfCell.addElement(nestedTable);

                    // Set border to NONE for the nested table cell
                    table.addCell(pdfCell);
                }
            }

            table.addCell(getCell(0, priceFormat(addZeroToPrice((sumMainPrice - sumDiscount) + factorResponseForAdmin.getShipment())), 10, 0, 0, 40, Element.ALIGN_CENTER , 0));
            table.addCell(getCell(0, "جمع کل پس از تخفیف مالیات ، عوارض ، هزینه ارسال ، هدیه (ریال)", 10, 0, 0, 40, Element.ALIGN_CENTER , 0));
        } else if (tableNum == 4) {
            PdfPCell pdfCell = getCell(1, "این برگ صرفا پیش فاکتور می باشد فاکتور اصلی به همراه کالا ارسال میشود", 10, 0, 0, 40, Element.ALIGN_CENTER , 0);
            pdfCell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(pdfCell);
        } else if (tableNum == 0) {

            ClassPathResource imageResource = new ClassPathResource("images/logo.png");
            InputStream is = imageResource.getInputStream();
            Image img = Image.getInstance(is.readAllBytes());
            img.scaleAbsolute(100, 50); // تنظیم عرض و ارتفاع دلخواه

            PdfPCell pdfCell1 = new PdfPCell(img, false);
            pdfCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            pdfCell1.setBorder(PdfPCell.NO_BORDER); // Optional: Remove the border if desired

            PdfPCell pdfCell2 = getCell(1, "پیش فاکتور", 10, 0, 0, 40, Element.ALIGN_CENTER , 0);
            pdfCell2.setPhrase(new Phrase("پیش فاکتور", iran_yekan_bold));
            pdfCell2.setBorder(PdfPCell.NO_BORDER);

            LocalDateTime now = factorResponseForAdmin.getCartList().get(0).getFactor().getUpdate_at();
            LocalDate gregorianDate = now.toLocalDate();
            PersianDate persianDate = PersianDate.fromGregorian(gregorianDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String formattedPersianDate = persianDate.format(formatter);

            PdfPCell pdfCell3 = getCell(1,     "تاریخ : " + formattedPersianDate , 10, 0, 0, 40, Element.ALIGN_CENTER , 0);
            pdfCell3.setBorder(PdfPCell.NO_BORDER);

            table.addCell(pdfCell3);
            table.addCell(pdfCell2);
            table.addCell(pdfCell1);


        }


    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A3.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        ///////////table 1//////////////////
        float[] widths = {0.1f, 0.1f, 0.1f, 0.5f, 0.1f};
        PdfPTable table1 = new PdfPTable(5);
        table1.setWidthPercentage(100f);
        table1.setWidths(widths);
        table1.setSpacingBefore(10);
        writeTableData(table1, 1);


        ///////////table 2//////////////////
        float[] widthsProductTable = {0.4f, 0.2f, 0.2f, 0.2f, 0.1f, 0.5f, 0.2f, 0.1f};
        PdfPTable table2 = new PdfPTable(8);
        table2.setWidthPercentage(100f);
        table2.setWidths(widthsProductTable);
        table2.setSpacingBefore(10);
        writeTableHeader(table2);
        writeTableData(table2, 2);


        ///////////table 3//////////////////
        float[] widthsPriceTable = {0.2f, 0.75f};
        PdfPTable table3 = new PdfPTable(2);
        table3.setWidthPercentage(100f);
        table3.setWidths(widthsPriceTable);
        table3.setSpacingBefore(10);
        writeTableData(table3, 3);


        ///////////table 4//////////////////
        float[] widthsNoticeTable = {0.1f};
        PdfPTable table4 = new PdfPTable(1);
        table4.setWidthPercentage(100f);
        table4.setWidths(widthsNoticeTable);
        table4.setSpacingBefore(10);
        writeTableData(table4, 4);


        ///////////table 0//////////////////
        float[] widthsHeadTable = {0.1f , 0.1f , 0.1f};
        PdfPTable table0 = new PdfPTable(3);
        table0.setWidthPercentage(100f);
        table0.setWidths(widthsHeadTable);
        table0.setSpacingBefore(10);
        writeTableData(table0, 0);


        document.add(table0);
        document.add(table1);
        document.add(table2);
        document.add(table3);
        document.add(table4);

        document.close();


    }


    private PdfPCell getCell(int colspan, String text, int paddingTop, int paddingBottom, int paddingRight, int height, int position , int border) throws IOException {
        PdfPCell cell = new PdfPCell(new Phrase(text, iran_yekan_light));
        cell.setHorizontalAlignment(position);
        cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        cell.setPaddingTop(paddingTop);
        cell.setPaddingRight(paddingRight);
        cell.setPaddingBottom(paddingBottom);
        if (colspan == 1) {
            cell.setRowspan(2);
        }
        cell.setFixedHeight(height);
        if (border==1){
            cell.setBorder(PdfPCell.NO_BORDER);
        }

        return cell;
    }

    private String breakLine(String word1, String word2) {
        return word1 + "\n \n" + word2;
    }

    private String stringBuild(String phrase1, String phrase2, String phrase3, String phrase4, String phrase5, String phrase6) {
        String buyer = "خریدار : ";
        String email = "کد ملی : ";
        String address = "آدرس : ";
        String mobile = "موبایل : ";
        String tell = "تلفن : ";
        String postalCode = "کدپستی : ";

        return String.format(buyer + "\t%s\t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t " +
                " " + email + " \t %s\n \n " + address + " \t%s \n \n " + mobile + "\t%s \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t" + tell + "\t%s \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t " + postalCode + "\t%s \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t         ", phrase1, phrase2, phrase3, phrase4, phrase5, phrase6);
    }


    public String priceFormat(long price) {

        Locale persianLocale = new Locale("fa", "IR");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(persianLocale);
        decimalFormat.applyPattern("#,###");
        return decimalFormat.format(price);
    }


    public long addZeroToPrice(long price) {
        String num = String.valueOf(price);
        num += "0";
        return Long.parseLong(num);
    }

}