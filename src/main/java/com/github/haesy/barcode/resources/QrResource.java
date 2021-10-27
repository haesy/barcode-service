package com.github.haesy.barcode.resources;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.github.haesy.barcode.exceptions.ClientException;
import com.github.haesy.barcode.util.BitMatrixStreamingOutput;
import com.github.haesy.barcode.util.Color;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Resource that generates images of QR-Codes.
 * 
 * @see <a href= "https://en.wikipedia.org/wiki/QR_code">QR-Code - Wikipedia</a>
 * @author Yannick Häßler
 */
@Path("qr")
public class QrResource
{
    private static final Charset JIS_X_0208 = Charset.forName("x-JIS0208");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]+");
    private static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("[0-9A-Z" + Pattern.quote(" $%*+-./:") + "]+");

    private static final QRCodeWriter QR_WRITER = new QRCodeWriter();

    @GET
    public Response getQrCode(@QueryParam("content") String content, @QueryParam("size") @DefaultValue("0") int size,
            @QueryParam("error-correction") @DefaultValue("L") ErrorCorrectionLevel errorCorrection,
            @QueryParam("version") Integer version, @QueryParam("margin") Integer margin,
            @QueryParam("color") @DefaultValue("000000") Color fgColor,
            @QueryParam("bg-color") @DefaultValue("FFFFFF") Color bgColor)
    {
        if (content == null || content.length() == 0)
        {
            throw new ClientException("content must not be empty");
        }
        final Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);

        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);

        if (version != null)
        {
            validateVersion(version);
            hints.put(EncodeHintType.QR_VERSION, version);
        }

        final InputMode type = InputMode.forString(content);
        hints.put(EncodeHintType.CHARACTER_SET, type.charsetName());

        if (margin != null)
        {
            hints.put(EncodeHintType.MARGIN, margin);
        }

        final BitMatrix matrix = encode(content, size, hints);
        final MatrixToImageConfig config = new MatrixToImageConfig(fgColor.intValue(), bgColor.intValue());
        return new BitMatrixStreamingOutput(matrix, config).toResponseBuilder().build();
    }

    /**
     * Validates, that the requested version is in the allowed range (1 to 40).
     */
    static void validateVersion(int requestedVersion)
    {
        if (requestedVersion < 1 || requestedVersion > 40)
        {
            throw new ClientException("version must be between 1 and 40");
        }
    }

    static BitMatrix encode(String content, int size, Map<EncodeHintType, Object> hints)
    {
        try
        {
            return QR_WRITER.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
        }
        catch (WriterException | IllegalArgumentException | NullPointerException ex)
        {
            throw new ClientException("failed to encode QR-Code: " + ex.getMessage(), ex);
        }
    }

    public enum InputMode
    {
        // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
        NUMERIC(NUMERIC_PATTERN),
        // 0–9, A–Z (upper-case only), space, $, %, *, +, -, ., /, :
        ALPHANUMERIC(ALPHA_NUMERIC_PATTERN),
        // ISO 8859-1 - https://en.wikipedia.org/wiki/ISO/IEC_8859-1
        BINARY(StandardCharsets.ISO_8859_1),
        // Shift JIS X 0208 - https://en.wikipedia.org/wiki/JIS_X_0208
        KANJI_KANA(JIS_X_0208),
        // Supported via ECI or heuristics
        // https://en.wikipedia.org/wiki/Extended_Channel_Interpretation
        UTF_8(StandardCharsets.UTF_8);

        private static final InputMode[] STANDARD_MODES = new InputMode[]
        { NUMERIC, ALPHANUMERIC, BINARY, KANJI_KANA };

        private final Predicate<String> matcher;
        private final String charset;

        private InputMode(Pattern pattern)
        {
            matcher = pattern.asMatchPredicate();
            charset = StandardCharsets.ISO_8859_1.name();
        }

        private InputMode(Charset charset)
        {
            this.matcher = s -> charset.newEncoder().canEncode(s);
            this.charset = charset.name();
        }

        public Predicate<String> asPredicate()
        {
            return matcher;
        }

        public String charsetName()
        {
            return charset;
        }

        public static InputMode forString(String content)
        {
            for (InputMode type : STANDARD_MODES)
            {
                if (type.asPredicate().test(content))
                {
                    return type;
                }
            }
            return UTF_8;
        }
    }
}
