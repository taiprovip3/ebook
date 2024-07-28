package phb.ebookstore.dev.enumric;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public enum Genre {
	
	//Literary genres
	FICTION("Fiction", GenreGroup.LITERATURES), // Hư cấu (Kể chuyện không có thật)
    NON_FICTION("Non-fiction", GenreGroup.LITERATURES), // Phi hư cấu (Dựa trên sự thật và thực tế)
    MYSTERY("Mystery", GenreGroup.LITERATURES), // Bí ẩn (Kể về các vụ án hoặc tình huống cần giải quyết)
    THRILLER("Thriller", GenreGroup.LITERATURES), // Hồi hộp (Tạo ra sự hồi hộp và căng thẳng)
    FANTASY("Fantasy", GenreGroup.LITERATURES), // Huyền bí (Kể về các thế giới huyền bí và kỳ ảo)
    ROMANCE("Romance", GenreGroup.LITERATURES), // Lãng mạn (Tập trung vào các mối quan hệ tình cảm)
    HISTORICAL("Historical", GenreGroup.LITERATURES),         // Lịch sử (Dựa trên các sự kiện và thời kỳ lịch sử)
    POETRY("Poetry", GenreGroup.LITERATURES), // Thơ (Tác phẩm sử dụng ngôn ngữ thơ ca)
    DRAMA("Drama", GenreGroup.LITERATURES),              // Kịch (Tập trung vào các tình huống và mâu thuẫn kịch tính)
    HORROR("Horror", GenreGroup.LITERATURES),	// Kinh dị (Tạo ra sự sợ hãi và lo lắng)
    SCIENCE_FICTION("Science Fiction", GenreGroup.LITERATURES), // Khoa học viễn tưởng (Dựa trên các ý tưởng khoa học giả tưởng)
    CONTEMPORARY("Contemporary", GenreGroup.LITERATURES), // Đương đại
    CLASSICS("Classics", GenreGroup.LITERATURES), // Kinh điển
    ADVENTURE("Adventure", GenreGroup.LITERATURES), // Phiêu lưu
    DYSTOPIAN("Dystopian", GenreGroup.LITERATURES), // Hậu tận thế
    COMICS("Comics", GenreGroup.LITERATURES),		// Truyện cười
    
    // Kinh tế
    MICROECONOMICS("Microeconomics", GenreGroup.ECONOMICS), // Kinh tế vi mô
    MACROECONOMICS("Macroeconomics", GenreGroup.ECONOMICS), // Kinh tế vĩ mô
    ECONOMIC_THEORY("Economic Theory", GenreGroup.ECONOMICS), // Lý thuyết kinh tế
    DEVELOPMENT_ECONOMICS("Development Economics", GenreGroup.ECONOMICS), // Kinh tế phát triển
    INTERNATIONAL_ECONOMICS("International Economics", GenreGroup.ECONOMICS), // Kinh tế quốc tế
    BEHAVIORAL_ECONOMICS("Behavioral Economics", GenreGroup.ECONOMICS), // Kinh tế hành vi
    PUBLIC_ECONOMICS("Public Economics", GenreGroup.ECONOMICS), // Kinh tế công
    ENVIRONMENTAL_ECONOMICS("Environmental Economics", GenreGroup.ECONOMICS), // Kinh tế môi trường
    ECONOMETRICS("Econometrics", GenreGroup.ECONOMICS), // Kinh tế lượng
    LABOR_ECONOMICS("Labor Economics", GenreGroup.ECONOMICS), // Kinh tế lao động
    FINANCIAL_ECONOMICS("Financial Economics", GenreGroup.ECONOMICS), // Kinh tế tài chính
    HEALTH_ECONOMICS("Health Economics", GenreGroup.ECONOMICS), // Kinh tế y tế
    INDUSTRIAL_ORGANIZATION("Industrial Organization", GenreGroup.ECONOMICS), // Tổ chức công nghiệp
    
    // Tâm Lý Kĩ Năng Sống
    SELF_HELP("Self Help", GenreGroup.SPYCHOLOGIES), // Tự giúp đỡ bản thân
    PERSONAL_DEVELOPMENT("Personal Development", GenreGroup.SPYCHOLOGIES), // Phát triển cá nhân
    EMOTIONAL_INTELLIGENCE("Emotional Intelligence", GenreGroup.SPYCHOLOGIES), // Trí tuệ cảm xúc
    MINDFULNESS("Mindfulness", GenreGroup.SPYCHOLOGIES), // Chánh niệm
    STRESS_MANAGEMENT("Stress Management", GenreGroup.SPYCHOLOGIES), // Quản lý căng thẳng
    TIME_MANAGEMENT("Time Management", GenreGroup.SPYCHOLOGIES), // Quản lý thời gian
    COMMUNICATION_SKILLS("Communication Skills", GenreGroup.SPYCHOLOGIES), // Kỹ năng giao tiếp
    CONFLICT_RESOLUTION("Conflict Resolution", GenreGroup.SPYCHOLOGIES), // Giải quyết xung đột
    MOTIVATION("Motivation", GenreGroup.SPYCHOLOGIES), // Động lực
    LEADERSHIP("Leadership", GenreGroup.SPYCHOLOGIES), // Lãnh đạo
    RESILIENCE("Resilience", GenreGroup.SPYCHOLOGIES), // Khả năng phục hồi
    DECISION_MAKING("Decision Making", GenreGroup.SPYCHOLOGIES), // Ra quyết định
    RELATIONSHIP_BUILDING("Relationship Building", GenreGroup.SPYCHOLOGIES), // Xây dựng mối quan hệ
    SELF_AWARENESS("Self Awareness", GenreGroup.SPYCHOLOGIES), // Nhận thức về bản thân
    
    // Sách Thiếu Nhi
    PICTURE_BOOKS("Picture Books", GenreGroup.CHILDRENS), // Sách tranh (Dành cho trẻ nhỏ với hình ảnh minh họa nhiều)
    BOARD_BOOKS("Board Books", GenreGroup.CHILDRENS), // Sách bìa cứng (Sách có bìa cứng, thường dành cho trẻ sơ sinh và trẻ nhỏ)
    EARLY_READERS("Early Readers", GenreGroup.CHILDRENS), // Sách đọc sớm (Dành cho trẻ mới học đọc với văn bản đơn giản)
    CHAPTER_BOOKS("Chapter Books", GenreGroup.CHILDRENS), // Sách chương (Dành cho trẻ em lớn hơn với các chương ngắn và câu chuyện dài hơn)
    MIDDLE_GRADE("Middle Grade", GenreGroup.CHILDRENS), // Sách trung học (Dành cho lứa tuổi từ 8-12 với nội dung phức tạp hơn)
    YOUNG_ADULT("Young Adult", GenreGroup.CHILDRENS), // Sách thiếu niên (Dành cho tuổi thiếu niên với các chủ đề trưởng thành hơn)
    FAIRY_TALES("Fairy Tales", GenreGroup.CHILDRENS), // Truyện cổ tích (Câu chuyện kỳ ảo và phép thuật)
    EDUCATIONAL("Educational", GenreGroup.CHILDRENS), // Giáo dục (Sách giúp trẻ học hỏi về các chủ đề giáo dục)
    ANIMAL_STORIES("Animal Stories", GenreGroup.CHILDRENS), // Câu chuyện về động vật (Câu chuyện với các nhân vật động vật)
    EASY_READERS("Easy Readers", GenreGroup.CHILDRENS), // Sách đọc dễ (Sách dành cho trẻ mới học đọc, với văn bản đơn giản và hình ảnh hỗ trợ)
    HUMOR("Humor", GenreGroup.CHILDRENS), // Hài hước (Câu chuyện với các yếu tố hài hước để giải trí)
    BEDTIME_STORIES("Bedtime Stories", GenreGroup.CHILDRENS), // Câu chuyện trước khi đi ngủ (Sách với các câu chuyện nhẹ nhàng để giúp trẻ em dễ ngủ)
    FABLES("Fables", GenreGroup.CHILDRENS), // Huyền thoại (Câu chuyện ngắn có bài học đạo đức thường là với các nhân vật động vật)
    
    // Nuôi Dạy con
    PARENTING_GUIDES("Parenting Guides", GenreGroup.EDUCATIONS), // Hướng dẫn nuôi dạy con (Sách cung cấp các chỉ dẫn và chiến lược về nuôi dạy con cái)
    CHILD_DEVELOPMENT("Child Development", GenreGroup.EDUCATIONS), // Phát triển trẻ em (Sách về sự phát triển của trẻ em qua các giai đoạn tuổi)
    POSITIVE_PARENTING("Positive Parenting", GenreGroup.EDUCATIONS), // Nuôi dạy tích cực (Sách về các phương pháp nuôi dạy con cái theo cách tích cực và khuyến khích)
    DISCIPLINE("Discipline", GenreGroup.EDUCATIONS), // Kỷ luật (Sách về các phương pháp và chiến lược kỷ luật hiệu quả)
    PARENTING_STYLES("Parenting Styles", GenreGroup.EDUCATIONS), // Phong cách nuôi dạy (Khám phá các phong cách và phương pháp nuôi dạy con cái khác nhau)
    SINGLE_PARENTING("Single Parenting", GenreGroup.EDUCATIONS), // Nuôi dạy con một mình (Sách dành cho cha mẹ đơn thân với các chiến lược và hỗ trợ)
    WORK_LIFE_BALANCE("Work-Life Balance", GenreGroup.EDUCATIONS), // Cân bằng công việc và cuộc sống (Chiến lược để cân bằng giữa công việc và trách nhiệm nuôi dạy con cái)
    PARENTING_CHALLENGES("Parenting Challenges", GenreGroup.EDUCATIONS), // Thách thức trong nuôi dạy (Khám phá các thách thức phổ biến và cách giải quyết chúng)
    SPECIAL_NEEDS("Special Needs", GenreGroup.EDUCATIONS), // Nhu cầu đặc biệt (Sách dành cho cha mẹ của trẻ em có nhu cầu đặc biệt hoặc khuyết tật)
    FAMILY_RELATIONSHIPS("Family Relationships", GenreGroup.EDUCATIONS), // Mối quan hệ gia đình (Khám phá các mối quan hệ trong gia đình và cách cải thiện chúng)
    PARENTING_TIPS("Parenting Tips", GenreGroup.EDUCATIONS), // Mẹo nuôi dạy (Các mẹo và lời khuyên ngắn gọn về nuôi dạy con cái)
    
    // Tiểu sử - Hồi Ký
    AUTOBIOGRAPHY("Autobiography", GenreGroup.BIOGRAPHIES), // Tự truyện (Câu chuyện về cuộc đời của chính tác giả)
    MEMOIR("Memoir", GenreGroup.BIOGRAPHIES), // Hồi ký (Những kỷ niệm và trải nghiệm cá nhân của tác giả)
    BIOGRAPHY("Biography", GenreGroup.BIOGRAPHIES), // Tiểu sử (Câu chuyện về cuộc đời của một người khác, do người khác viết)
    DIARY("Diary", GenreGroup.BIOGRAPHIES), // Nhật ký (Ghi chép hàng ngày hoặc định kỳ về cuộc sống và suy nghĩ cá nhân)
    PERSONAL_NARRATIVE("Personal Narrative", GenreGroup.BIOGRAPHIES), // Tường thuật cá nhân (Câu chuyện về các sự kiện và trải nghiệm cá nhân)
    AUTOBIOGRAPHICAL_NOVEL("Autobiographical Novel", GenreGroup.BIOGRAPHIES), // Tiểu thuyết tự truyện (Tiểu thuyết dựa trên cuộc đời của tác giả, nhưng được viết dưới dạng hư cấu)
    HISTORICAL_MEMOIR("Historical Memoir", GenreGroup.BIOGRAPHIES), // Hồi ký lịch sử (Ký ức và trải nghiệm cá nhân liên quan đến các sự kiện lịch sử)
    CELEBRITY_MEMOIR("Celebrity Memoir", GenreGroup.BIOGRAPHIES), // Hồi ký của người nổi tiếng (Những kỷ niệm và trải nghiệm cá nhân của các nhân vật nổi tiếng)
    
    // Sách giáo khoa - tham khảo
    TEXTBOOK("Textbook", GenreGroup.TEXTBOOKS), // Sách giáo khoa (Sách được thiết kế để giảng dạy trong các khóa học và chương trình học)
    REFERENCE_BOOK("Reference Book", GenreGroup.TEXTBOOKS), // Sách tham khảo (Sách cung cấp thông tin chi tiết và hỗ trợ nghiên cứu)
    WORKBOOK("Workbook", GenreGroup.TEXTBOOKS), // Sách bài tập (Sách chứa bài tập và hoạt động thực hành để củng cố kiến thức)
    GUIDE("Guide", GenreGroup.TEXTBOOKS), // Hướng dẫn (Sách cung cấp hướng dẫn và chỉ dẫn cho một chủ đề cụ thể)
    HANDBOOK("Handbook", GenreGroup.TEXTBOOKS), // Cẩm nang (Sách chứa thông tin tổng quan và hướng dẫn về một lĩnh vực cụ thể)
    ENCYCLOPEDIA("Encyclopedia", GenreGroup.TEXTBOOKS), // Từ điển bách khoa (Tài liệu chứa thông tin chi tiết về nhiều chủ đề khác nhau)
    ATLAS("Atlas", GenreGroup.TEXTBOOKS), // Atlas (Sách chứa bản đồ và thông tin địa lý)
    DICTIONARY("Dictionary", GenreGroup.TEXTBOOKS), // Từ điển (Sách cung cấp định nghĩa, phát âm và thông tin về từ ngữ)
    MANUAL("Manual", GenreGroup.TEXTBOOKS), // Sổ tay (Hướng dẫn sử dụng và các chỉ dẫn kỹ thuật)
    TEXTBOOK_SERIES("Textbook Series", GenreGroup.TEXTBOOKS), // Bộ sách giáo khoa (Một loạt các sách giáo khoa liên quan đến một môn học hoặc chương trình học)
    
   // Học Ngoại Ngữ
    LANGUAGE_TEXTBOOK("Language Textbook", GenreGroup.LANGUAGES), // Sách giáo khoa ngoại ngữ (Sách học tập dành riêng cho việc học ngoại ngữ)
    GRAMMAR_GUIDE("Grammar Guide", GenreGroup.LANGUAGES), // Hướng dẫn ngữ pháp (Sách cung cấp quy tắc và bài tập ngữ pháp cho một ngôn ngữ)
    VOCABULARY_BOOK("Vocabulary Book", GenreGroup.LANGUAGES), // Sách từ vựng (Sách giúp học và ôn tập từ vựng trong một ngôn ngữ)
    LANGUAGE_WORKBOOK("Language Workbook", GenreGroup.LANGUAGES), // Sách bài tập ngoại ngữ (Sách chứa các bài tập và hoạt động để luyện tập kỹ năng ngôn ngữ)
    PHRASEBOOK("Phrasebook", GenreGroup.LANGUAGES), // Sách cụm từ (Sách chứa các cụm từ và câu phổ biến trong một ngôn ngữ)
    CONVERSATION_GUIDE("Conversation Guide", GenreGroup.LANGUAGES), // Hướng dẫn hội thoại (Sách cung cấp các tình huống hội thoại và cách phản ứng trong ngôn ngữ học)
    AUDIO_LINGUAL_BOOK("Audio-Lingual Book", GenreGroup.LANGUAGES), // Sách học ngôn ngữ qua audio (Sách kết hợp với các tài liệu âm thanh để học ngôn ngữ)
    LANGUAGE_EXERCISES("Language Exercises", GenreGroup.LANGUAGES), // Bài tập ngôn ngữ (Sách chứa các bài tập luyện kỹ năng ngôn ngữ)
    LANGUAGE_COURSEBOOK("Language Coursebook", GenreGroup.LANGUAGES), // Sách khóa học ngôn ngữ (Sách cấu trúc các bài học và chương trình học cho ngôn ngữ)
    LANGUAGE_DICTIONARY("Language Dictionary", GenreGroup.LANGUAGES), // Từ điển ngôn ngữ (Sách cung cấp định nghĩa và cách sử dụng từ trong một ngôn ngữ)
    CULTURE_GUIDE("Culture Guide", GenreGroup.LANGUAGES), // Hướng dẫn văn hóa (Sách cung cấp thông tin về văn hóa và phong tục của các quốc gia sử dụng ngôn ngữ học)
    LANGUAGE_ATHLETICS("Language Athletics", GenreGroup.LANGUAGES), // Sách luyện tập ngôn ngữ (Sách cung cấp các bài tập và hoạt động tăng cường kỹ năng ngôn ngữ)
    
    // Cuộc sống Lành mạnh
    COOKING("Cooking", GenreGroup.OTHERS),
    TRAVEL("Travel", GenreGroup.OTHERS),
    HEALTH("Health", GenreGroup.OTHERS),
    ART("Art", GenreGroup.OTHERS),
    MUSIC("Music", GenreGroup.OTHERS),
    RELIGION("Religion", GenreGroup.OTHERS),
    SPIRITUALITY("Spirituality", GenreGroup.OTHERS),
    SCIENCE("Science", GenreGroup.OTHERS),
    PHILOSOPHY("Philosophy", GenreGroup.OTHERS),
    BUSINESS("Business", GenreGroup.OTHERS),
    EDUCATION("Education", GenreGroup.OTHERS),
    GRAPHIC_NOVEL("Graphic Novel", GenreGroup.OTHERS),
    SPORTS("Sports", GenreGroup.OTHERS),
    TECHNOLOGY("Technology", GenreGroup.OTHERS);
	
	private final String displayName;
	private final GenreGroup group;

    Genre(String displayName, GenreGroup group) {
        this.displayName = displayName;
        this.group = group;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public GenreGroup getGroup() {
        return group;
    }
    
    private static final EnumMap<GenreGroup, Set<Genre>> genresByGroup = new EnumMap<>(GenreGroup.class);
    
    static {
        for (Genre genre : Genre.values()) {
            genresByGroup.computeIfAbsent(genre.getGroup(), k -> EnumSet.noneOf(Genre.class)).add(genre);
        }
    }
    
    public static Set<Genre> getGenresByGroup(GenreGroup group) {
        return genresByGroup.getOrDefault(group, EnumSet.noneOf(Genre.class));
    }
}
