import java.lang.Math

open class Creature(_name: String, _type: String, _attack: Int, _defense: Int, _health: Int, _damage: Int)
{
    var attack = 0
        set(value)
        {
            if(value !in 1..30) {
                println("\nНекорректное значение параметра Атака, будет выбрано случайное число из диапазона")
                field = (1..30).random()
            }
            else
                field = value
        }
        get()
        {
            return field
        }

    var defense = 0
        set(value)
        {
            if(value !in 1..30) {
                println("\nНекорректное значение параметра Защита, будет выбрано случайное число из диапазона")
                field = (1..30).random()
            }
            else
                field = value
        }
        get()
        {
            return field
        }

    var health = 0
        set(value)
        {
            if(value !in 1..healthLimit)
            {
                println("\nНекорректное значение параметра Здоровье, будет выбрано случайное число из диапазона")
                field = (1..healthLimit).random()
            }
            else
                field = value
        }
        get()
        {
            return field
        }

    var damage = 0
        set(value)
        {
            if(value !in damageLowerBound..damageUpperBound)
            {
                println("\nНекорректное значение параметра Урон, будет выбрано случайное число из диапазона")
                field = (damageLowerBound..damageUpperBound).random()
            }
            else
                field = value
        }
        get()
        {
            return field
        }

    var name = "Неизвестный"
    var type = "Существо"
    var maxHealth = 0

    init {
        attack = _attack
        defense = _defense
        health = _health
        maxHealth = health
        damage = _damage
        if(_name.isNotBlank())
            name = _name
        if(_type.isNotBlank())
            type = _type
    }

    fun printCreatureStats()
    {
        println("Атака: $attack\nЗащита: $defense\nЗдоровье: $health\nУрон: $damage")
    }

    var isDead = false

    open fun attackAnotherCreature(enemy: Creature)
    {
        println("$type $name атакует ${enemy.type} ${enemy.name}!")

        var attackModifier = Math.abs(attack - enemy.defense) + 1

        for(i in 1..attackModifier)
        {
            if((1..6).random() > 4)
            {
                var earnedDamage = (1..damage).random()
                println("Успешная атака! $type $name нанёс ${enemy.type} ${enemy.name} $earnedDamage урона!")
                if(enemy.health - earnedDamage <= 0)
                {
                    println("${enemy.type} ${enemy.name} убит!\n")
                    enemy.isDead = true
                }
                else {
                    enemy.health -= earnedDamage
                    println("У него осталось ${enemy.health} очков здоровья!\n")
                }
                return
            }
        }

        println("Промах, нанесено 0 урона!\n")
    }
}