import kotlin.math.max

class Player(_name: String, _type: String, _attack: Int, _defense: Int, _health: Int, _damage: Int):
    Creature(_name, _type, _attack, _defense, _health, _damage)
{
    var healPotions = 4

    fun healYourself(): Boolean {
        if(healPotions == 0)
        {
            println("У Вас осталось 0 зелий здоровья, лечение невозможно.\n")
            return false
        }
        else
        {
            if(health == maxHealth)
            {
                println("У Вас максимальный уровень здоровья, лечение невозможно.")
                return false
            }
            else {
                var heal = (maxHealth * 0.3f).toInt()
                if(health + heal > maxHealth)
                    health = maxHealth
                else
                    health += heal
                healPotions--
                println("Зелье добавляет Вам $heal очков здоровья, " +
                        "Ваше текущее здоровье равно $health, " +
                        "у Вас осталось $healPotions зелий здоровья.\n")
                return true
            }
        }
    }
}