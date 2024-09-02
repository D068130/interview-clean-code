import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author autor
 */
@Slf4j
public class GHUtils {

    private GHUtils() {
    }


    public static final List<String> TAGS_FIELDS_LIST = List.of("position", "category");
    public static final List<String> OBJECT_FIELDS_LIST = List.of("name", "number", "type");

    /**
     * Generate hash for map key and values
     *
     * @param map
     * @return
     */
    public static String generate(final Map<String, String> map, boolean isObject) {
        if (map.isEmpty())return "0";

        Map<String, String> filteredMap;
        if (isObject) {
            filteredMap = map.entrySet().stream().filter(mapEntry -> OBJECT_FIELDS_LIST.contains(mapEntry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            filteredMap = map.entrySet().stream().filter(mapEntry -> TAGS_FIELDS_LIST.contains(mapEntry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        /** Sorting map to generate same hash even if tags are not in same order but with same key value pair*/
        Map<String, String> sortedMap = filteredMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Sorted Map" + sortedMap);

        final StringBuilder uniqueIdBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            uniqueIdBuilder.append(entry.getKey()).append(entry.getValue());
        }
        int hashCode = uniqueIdBuilder.toString().hashCode();
        String uniqueId = Integer.toHexString(hashCode);

        return uniqueId;
    }

    public static boolean isNumerical(String value) {
        log.debug(" isNumerical  ", value);
        /**   Check if int */
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            /**   Not int */
        }
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            /**   Not Long */
        }
        return false;
    }

    public static boolean isNumericFlexible(String value) {
        log.debug(" isNumerical Flexible ", value);

        /**  Check if float */
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            /**  Not float */
        }
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            /**   Not double */
        }
        return false;
    }

}






