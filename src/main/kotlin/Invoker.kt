import commands.Command


class Invoker( commands: HashMap<Int, Command>) {

    private val commands = commands

    fun start() {
        while (true){
            println("Enter a number or `q` if you want to quit")
            commands.forEach { (number, command) ->
                println("$number. $command")
            }
            val input = readln()
            if (input == "q")
                break

            val number = input.toIntOrNull()

            if (number == null || commands[number] == null) {
                println("Wrong input! Repeat again.")
                continue
            }
            commands[number]?.execute()
        }
    }

}