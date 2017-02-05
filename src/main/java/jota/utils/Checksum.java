package jota.utils;

import jota.pow.JCurl;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by pinpong on 02.12.16.
 */
public class Checksum {

    public static String addChecksum(String address) {
        InputValidator.checkAddress(address);
        String addressWithChecksum = address;
        addressWithChecksum += calculateChecksum(address);
        return addressWithChecksum;
    }

    public static String removeChecksum(String addressWithChecksum) {
        if (isAddressWithChecksum(addressWithChecksum)) {
            return getAddress(addressWithChecksum);
        }
        return StringUtils.EMPTY;
    }

    private static String getAddress(String addressWithChecksum) {
        return addressWithChecksum.substring(0, Constants.ADDRESS_LENGTH_WITHOUT_CHECKSUM);
    }

    public static boolean isValidChecksum(String addressWithChecksum) {
        String addressWithoutChecksum = removeChecksum(addressWithChecksum);
        String addressWithRecalculateChecksum = addressWithChecksum += calculateChecksum(addressWithoutChecksum);
        return addressWithRecalculateChecksum.equals(addressWithChecksum);
    }

    public static boolean isAddressWithChecksum(String address) {
        return InputValidator.checkAddress(address) && address.length() == Constants.ADDRESS_LENGTH_WITH_CHECKSUM;
    }

    public static boolean isAddressWithoutChecksum(String address) {
        return InputValidator.checkAddress(address) && address.length() == Constants.ADDRESS_LENGTH_WITHOUT_CHECKSUM;
    }

    public static String calculateChecksum(String address) {
        JCurl curl = new JCurl();
        curl.reset();
        curl.setState(Converter.copyTrits(address, curl.getState()));
        curl.transform();
        return Converter.trytes(curl.getState()).substring(0, 9);
    }
}
