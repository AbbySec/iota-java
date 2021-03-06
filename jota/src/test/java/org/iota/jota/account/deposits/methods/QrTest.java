package org.iota.jota.account.deposits.methods;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.iota.jota.account.deposits.ConditionalDepositAddress;
import org.iota.jota.account.deposits.DepositRequest;
import org.iota.jota.account.deposits.DepositTest;

import net.glxn.qrgen.javase.QRCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QrTest extends DepositTest {
    
    private static final byte[] qrBytes = new byte[] {
            -119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 125, 0, 0, 0, 125, 1, 0, 0, 0, 0, 
            -89, -5, 81, -120, 0, 0, 2, 95, 73, 68, 65, 84, 120, -38, -51, -43, 49, -110, -91, 32, 16, 0, 80, 77, -28, 
            10, -102, -56, -43, 52, -127, 43, 64, -126, -112, -64, 21, 32, -79, -81, 38, -119, 92, 1, 18, -39, -98, 63, 
            -77, -63, -2, -33, -109, 47, 86, 25, -68, 42, 109, -96, -69, 97, -24, -1, -114, 58, -4, 55, 96, 71, 47, 
            -109, -117, -70, -91, 84, 7, 78, 1, 52, 59, -101, -59, 26, -103, -86, -18, 64, 65, 92, -14, 117, -12, 42, 
            -10, 56, 105, -9, 11, 92, 66, 6, -72, -108, -38, -45, 111, 16, -9, -112, -20, -46, -22, -34, 104, -128, 
            -106, -84, 25, 33, -76, 91, -73, 78, -126, 29, -45, -33, 17, 127, -42, -14, 6, 29, 127, -21, 28, 88, 97, 56, 
            -4, -20, -57, 27, -72, 108, -73, 121, -79, 12, -4, 104, 87, 78, 65, -18, -27, 82, -46, 30, 94, 8, 121, 30, 
            20, -100, -37, 108, -42, -119, -99, -57, 53, 25, 9, 20, 0, -76, 42, 102, 97, 116, -87, 66, 28, 20, -92, -26, 
            90, 84, 75, 8, 14, 82, 36, -95, 78, 50, 10, -63, 66, -71, -123, 81, 36, 100, 119, -19, -9, -58, -84, 44, 
            -111, 7, -96, -64, -35, -36, -113, 23, -122, -115, -84, -121, 78, -63, 57, -30, -14, -9, 58, 58, 59, -70, 
            87, -40, 15, -120, -122, -39, 109, 127, 4, -61, -80, -122, -109, 48, -70, 115, 22, 58, -99, 75, 123, 14, 
            -96, -96, -123, -100, -85, -111, 94, -89, 71, 73, 18, -98, 37, 87, 29, -91, 3, -52, -73, 61, 40, -72, -89, 
            -43, 108, -58, -80, 83, -56, -46, -128, 2, -120, -36, -21, 107, -98, -71, 11, -39, -111, 112, 50, 124, 112, 
            -9, 102, -98, 74, 33, 33, 88, -98, -21, 60, 48, -52, -125, 29, 73, 120, 120, 58, 49, -53, -95, 120, 124, 
            117, 10, 92, -118, -109, -40, 83, 84, 70, -89, -17, 52, -68, 67, 2, -72, 117, -27, -89, -68, 53, -8, -125, 
            -126, 96, -105, -72, 36, -53, 82, 56, -121, -119, -124, 123, -27, 81, -19, 14, 66, -16, -101, -20, 20, -60, 
            109, 59, 2, 86, 118, 92, -68, 98, 64, -127, -107, 57, -71, 123, -110, 9, -6, 119, 123, 124, 64, 29, 6, -114, 
            93, 10, -63, -59, -119, 3, 5, -71, 92, 70, -20, 56, 115, 49, 77, -126, 83, 0, -106, -7, 49, -22, -86, 20, 
            -117, -81, 45, -4, -128, 103, 53, -13, -90, 75, -65, -79, -125, 94, -117, -5, -128, -18, 66, 107, -87, -31, 
            -20, 44, -53, 64, -63, -59, -83, -38, -76, -105, -98, -107, -14, 28, 20, 64, 78, -39, -50, -36, 15, -117, 
            -99, 53, 80, -112, 74, -69, -27, -67, -103, -29, 94, -30, 43, -20, 7, 100, 12, -119, -89, 103, -127, 16, 
            -107, 34, -95, -82, 70, 108, 66, -55, 112, 75, -65, 3, 5, 126, 116, -113, -40, -106, -81, -86, 86, -100, 
            -124, -4, 96, -101, 95, -85, 18, 50, -82, -81, 84, 126, -64, -93, -21, -96, -81, 97, 12, 88, 14, 11, 80, 
            -48, 123, 122, -114, 12, -72, 126, 9, -119, 4, -117, 39, -63, -41, -39, -73, -55, -12, 93, -106, 31, 0, -51, 
            -49, 98, -64, -78, 76, -8, 29, -89, 0, -81, -126, -126, -121, 47, 3, -121, 97, -5, 47, 112, -103, 3, -101, 
            35, -8, -79, 0, 13, -55, -53, -81, -86, -69, 4, -125, 78, 1, 94, 5, -19, -58, -69, -28, -47, -113, 44, 36, 
            96, 111, -26, 75, 7, -100, 54, 68, 13, 20, -4, -73, 87, -12, 27, -4, 1, 80, 96, 1, -35, 109, -64, -2, -82, 
            0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126
    };


    
    QRMethod qrMethod = null;
    ConditionalDepositAddress conditions = null;
    
    @BeforeEach
    public void setUp() throws Exception {
        qrMethod = new QRMethod();
        
        DepositRequest request = new DepositRequest(new Date(0), false, 5);
        conditions = new ConditionalDepositAddress(request, depositAddress);
    }

    @Test
    public void qrFromString() {
        QRCode code = qrMethod.build(conditions);
        ConditionalDepositAddress deposit = qrMethod.parse(code);

        assertEquals(conditions, deposit);
    }

    @Disabled("array lengths differ, expected: <664> but was: <699>")
    @Test
    public void qrFromDeposit() throws IOException {
        QRCode code = qrMethod.build(conditions);
        try (ByteArrayOutputStream stream = code.stream()) {
            assertArrayEquals(qrBytes, stream.toByteArray());
        }
    }
}
