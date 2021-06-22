package it.reply.compliance.gdpr.identity.dto.comparator;

import java.util.Comparator;

import it.reply.compliance.gdpr.identity.dto.user.UserCountryDto;

public class UserComparators {

    public static Comparator<UserCountryDto> fullNameComparator() {
        return surnameComparator().thenComparing(nameComparator());
    }

    public static Comparator<UserCountryDto> nameComparator() {
        return (o1, o2) -> safeCompareIgnoreCase(o1.getName(), o2.getName());
    }

    public static Comparator<UserCountryDto> surnameComparator() {
        return (o1, o2) -> safeCompareIgnoreCase(o1.getSurname(), o2.getSurname());
    }

    private static int safeCompareIgnoreCase(String s1, String s2) {
        return (s1 == null ? "" : s1).compareToIgnoreCase(s2 == null ? "" : s2);
    }
}
