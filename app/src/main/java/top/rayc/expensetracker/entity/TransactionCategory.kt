package top.rayc.expensetracker.entity

data class TransactionCategory (
    val id: Int,
    val pid: Int,
    val name: String,
    val type: TransactionType = TransactionType.EXPENSE,
    val description: String,
    val children: List<TransactionCategory> = emptyList()
)

val defaultCategories = listOf(
    TransactionCategory(1, 0, "日常开销", TransactionType.EXPENSE, "日常生活的开销"),
    TransactionCategory(2, 1, "水气电费", TransactionType.EXPENSE, "日常的水电气费"),
    TransactionCategory(3, 1, "出行", TransactionType.EXPENSE, "出行的费用，坐车或单车费用"),
    TransactionCategory(4, 1, "食材", TransactionType.EXPENSE, "买菜花的钱"),
    TransactionCategory(5, 1, "外食", TransactionType.EXPENSE, "在外面吃饭花的钱"),
    TransactionCategory(6, 1, "日常购物", TransactionType.EXPENSE, "买日用品"),
    TransactionCategory(7, 1, "电话费", TransactionType.EXPENSE, "电话费"),
    TransactionCategory(8, 1, "房租", TransactionType.EXPENSE, "房租"),


    TransactionCategory(9, 0, "娱乐消费", TransactionType.EXPENSE, "娱乐消费记录"),
    TransactionCategory(10, 9, "零食", TransactionType.EXPENSE, "嘴馋买零食"),
    TransactionCategory(11, 9, "视频-电影-游戏", TransactionType.INCOME, "视频软件会员费，游戏、加速器等费用"),
    TransactionCategory(12, 9, "其他娱乐", TransactionType.EXPENSE, "旅游费用"),

    TransactionCategory(13, 0, "学习&开发", TransactionType.INCOME, "学习等的消费"),
    TransactionCategory(14, 13, "学习资料费", TransactionType.INCOME, "各种的学习资料费，专栏、视频课、学习网站会员"),
    TransactionCategory(15, 13, "服务器租用", TransactionType.INCOME, "云服务器租用费"),
    TransactionCategory(16, 13, "软件服务费", TransactionType.INCOME, "软件的一些费用"),
)