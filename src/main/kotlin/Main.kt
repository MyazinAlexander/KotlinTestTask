var healthLimit = 100
var damageLowerBound = 1
var damageUpperBound = 10

var attack = 0
var defense = 0
var health = 0
var damage = 0
var name = "Существо"

fun getNewCreatureStats()
{
    println("Имя: ")
    name = readLine()!!

    try {
        println("Атака (введите число от 1 до 30): ")
        attack = readLine()!!.toInt()
    } catch (nfe: NumberFormatException) {
        println("Ошибка ввода!")
    }

    try {
        println("Защита (введите число от 1 до 30): ")
        defense = readLine()!!.toInt()
    } catch (nfe: NumberFormatException) {
        println("Ошибка ввода!")
    }

    try {
        println("Здоровье (введите число от 1 до $healthLimit): ")
        health = readLine()!!.toInt()
    } catch (nfe: NumberFormatException) {
        println("Ошибка ввода!")
    }

    try {
        println("Урон (введите число от $damageLowerBound до $damageUpperBound): ")
        damage = readLine()!!.toInt()
    } catch (nfe: NumberFormatException) {
        println("Ошибка ввода!")
    }
}

fun createNewHero() : Player?
{
    var changeStatsOrContinue: Int? = 2
    var player: Player? = null
    while (changeStatsOrContinue != 1)
    {
        if(changeStatsOrContinue == 2) {
            getNewCreatureStats()
            player = Player(name, "Герой", attack, defense, health, damage)
            attack = 0
            defense = 0
            health = 0
            damage = 0
            println("\nТак выглядят характеристики героя:")
            player.printCreatureStats()
            println("Введите 1, если подтверждаете создание героя, либо 2, если желаете изменить его характеристики: ")
            try {
                changeStatsOrContinue = readLine()?.toInt()
                println()
            } catch (nfe: NumberFormatException) {
                println("\nОшибка ввода! Придётся создать персонажа заново!")
            }
        }
        else {
            println("Ошибка ввода! Придётся создать персонажа заново!")
            changeStatsOrContinue = 2
        }
    }
    return player
}

fun createNewMonster(): Monster?
{
    var changeStatsOrContinue: Int? = 2
    var monster: Monster? = null
    while (changeStatsOrContinue != 1)
    {
        if(changeStatsOrContinue == 2) {
            getNewCreatureStats()
            monster = Monster(name, "Монстр", attack, defense, health, damage)
            println("\nТак выглядят характеристики монстра:")
            monster.printCreatureStats()
            println("Введите 1, если подтверждаете создание монстра, либо 2, если желаете изменить его характеристики: ")
            try {
                changeStatsOrContinue = readLine()?.toInt()
                println()
            } catch (nfe: NumberFormatException) {
                println("Ошибка ввода! Придётся создать персонажа заново!\n")
            }
        }
        else {
            println("Ошибка ввода! Придётся создать персонажа заново!")
            changeStatsOrContinue = 2
        }
    }
    return monster
}

fun attackMonsters(monsters: MutableList<Monster?>, player: Player?)
{
    var aliveMonstersAmount = monsters.size
    var i = 0
    while(i < aliveMonstersAmount) {
        player!!.attackAnotherCreature(monsters[i]!!)
        if (monsters[i]!!.isDead) {
            monsters.remove(monsters[i])
            aliveMonstersAmount--
            i--
        }
        i++
    }
}

fun main(args: Array<String>) {
    println("Создайте  героя!")
    var player = createNewHero()

    var monstersAmount = -1
    var monstersAmountMax = 5

    while (monstersAmount < 0) {
        println("Введите, сколько будет монстров: ")
        try {
            monstersAmount = readLine()!!.toInt()
            if(monstersAmount > monstersAmountMax)
            {
                println("Слишком много монстров, попробуйте меньше.\n")
                monstersAmount = -1
            }
        } catch (nfe: NumberFormatException) {
            println("Ошибка ввода! Повторите, сколько будет монстров.\n")
        }
    }
    println()

    if (monstersAmount == 0)
        println("Враг сбежал.")

    var monsters: MutableList<Monster?> = mutableListOf()
    for (i in 1..monstersAmount) {
        println("Создайте монстра!")
        monsters.add(createNewMonster())
    }

    var count = 1
    var whoseMove = 1
    while (monsters.isNotEmpty()) {
        println("$count ход.\n*****\n")
        count++

        if (whoseMove == 1) {
            println("Что Вы желаете сделать?\n1 - атаковать монстров;\n2 - вылечиться.\nВведите соотвествующую цифру: ")
            var action = 1
            try {
                action = readLine()!!.toInt()
            } catch (nfe: NumberFormatException) {
                println("Ошибка ввода! По умолчанию Ваш герой атакует, это он сейчас и сделает.")
            }
            println()

            when (action) {
                1 -> {
                    attackMonsters(monsters, player)
                }

                2 -> {
                    if(!player!!.healYourself())
                    {
                        attackMonsters(monsters, player)
                    }
                }
            }
            whoseMove = 2
        } else {
            for (i in 0 until monsters.size) {
                monsters[i]!!.attackAnotherCreature(player!!)
                if (player!!.isDead) {
                    println("Вы проиграли, но в следующий раз Вам повезёт!")
                    return
                }
            }
            whoseMove = 1
        }

        println("Нажмите Enter для следующего хода.")
        readLine()
    }
    println("Добро одержало вверх, желаем Вам дальнейших побед!")
    return
}

