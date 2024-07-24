package ntp.dev.enumric;

import java.util.EnumSet;
import java.util.Set;

public enum GenreGroup {
	LITERATURES, // Văn học
	ECONOMICS, // Thuộc về kinh tế
	SPYCHOLOGIES, // Tâm lý & kỹ năng sống
	EDUCATIONS, // Giáo dục
	CHILDRENS, // Trẻ em
	BIOGRAPHIES, // Tiểu sử & hồi ký
	TEXTBOOKS, // Sách giáo khoa, hướng dẫn
	LANGUAGES, // Ngoại ngữ
	OTHERS;
	
	public static Set<GenreGroup> getAllGenreGroups() {
        return EnumSet.allOf(GenreGroup.class);
    }
}
