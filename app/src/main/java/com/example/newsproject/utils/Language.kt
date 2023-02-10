package com.example.newsproject.utils



object Language{
    private val languages: HashMap<String, HashMap<String, String>> = hashMapOf(
        Pair(
            "de",
            hashMapOf(
                Pair("general", "Allgemein"),
                Pair("business", "Business"),
                Pair("science", "Wissenschaft"),
                Pair("sports", "Sport"),
                Pair("health", "Gesundheit"),
                Pair("technology", "Technologie"),
                Pair("entertainment", "Unterhaltung"),
            ),
        ),
        Pair(
            "en",
            hashMapOf(
                Pair("general", "General"),
                Pair("business", "Business"),
                Pair("science", "Science"),
                Pair("sports", "Sports"),
                Pair("health", "Health"),
                Pair("technology", "Technology"),
                Pair("entertainment", "Entertainment"),
            ),
        ),
        Pair(
            "fr",
            hashMapOf(
                Pair("general", "Général"),
                Pair("business", "Affaires"),
                Pair("science", "Sciences"),
                Pair("sports", "Sport"),
                Pair("health", "Santé"),
                Pair("technology", "Technologie"),
                Pair("entertainment", "Divertissement"),
            ),
        ),
        Pair(
            "it",
            hashMapOf(
                Pair("general", "Generale"),
                Pair("business", "Business"),
                Pair("science", "Scienza"),
                Pair("sports", "Sportivo"),
                Pair("health", "Salute"),
                Pair("technology", "Tecnologia"),
                Pair("entertainment", "Intrattenimento"),
            ),
        ),
        Pair(
            "pt",
            hashMapOf(
                Pair("general", "Geral"),
                Pair("business", "Negocio"),
                Pair("science", "Ciência"),
                Pair("sports", "Desportivo"),
                Pair("health", "Saude"),
                Pair("technology", "Tecnologia"),
                Pair("entertainment", "Entretenimento"),
            ),
        ),
        Pair(
            "ru",
            hashMapOf(
                Pair("general", "Общие"),
                Pair("business", "Бизнес"),
                Pair("science", "Наука"),
                Pair("sports", "Спорт"),
                Pair("health", "Здоровье"),
                Pair("technology", "Технология"),
                Pair("entertainment", "Развлечение"),
            ),
        ),
        Pair(
            "zh",
            hashMapOf(
                Pair("general", "一般事务"),
                Pair("business", "业务"),
                Pair("science", "科学"),
                Pair("sports", "运动项目"),
                Pair("health", "健康"),
                Pair("technology", "科技"),
                Pair("entertainment", "娱乐节目"),
            ),
        ),
    )


    fun getCategoryNameByLanguage(language: String, category: String): String {
        return languages[language]?.get(category)?.toString() ?: "general"
    }
}

