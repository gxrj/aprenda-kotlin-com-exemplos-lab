enum class Nivel{
    BASICO {
        override fun toString() = "Básico"
    },
    INTERMEDIARIO {
        override fun toString() = "Intermediário"
    },
    AVANCADO {
        override fun toString() = "Avançado"
    };
}

data class Usuario( var nome: String, val email: String ) {
    override fun equals( other: Any? ): Boolean =
            this === other || other is Usuario && email == other?.email

    override fun hashCode(): Int = email.hashCode()

    override fun toString(): String = nome + "(${ email })"
}

data class ConteudoEducacional( var nome: String, val duracao: Int = 60 )

data class Formacao( var nome: String, val nivel: Nivel, val conteudos: List<ConteudoEducacional> ) {

    val inscritos = mutableListOf<Usuario>()

    fun matricular( usuario: Usuario ) {
        inscritos.add( usuario )
        print( "${ usuario.nome }(${ usuario.email }) foi matriculado(a) na " )
        println( "${ nome +" "+ nivel.toString() }".uppercase() )
    }

    fun exibirDetalhes() =
            println(
                "Nome: ${ nome } \nNível: ${ nivel }"+
                "\nDuração: ${ conteudos.map { it.duracao } .reduce { acc, next -> acc + next } } horas" +
                "\nCursos: \n${
                    conteudos.map { "- " + it.nome + " (" + it.duracao + " horas)" }
                            .reduce { acc, next -> "$acc\n$next" }
                }\n"
            )

    fun exibirInscritos() =
            println(
                "Relação de inscritos na ${ nome.uppercase() }: \n${
                    inscritos.map { it.toString() }
                            .reduce { acc, next -> "$acc\n$next" }
                }\n"
            )
}

fun criarFormacoes(): List<Formacao> {

    val nomes = arrayOf(
            "Introdução à lógica",      "Introdução à web",
            "Introdução ao Java",       "Introdução ao Spring Boot",
            "Introdução ao Javascript", "Introdução ao React",
            "Introdução ao Kotlin",     "Introdução ao Android" )

    val conteudos = mutableListOf<ConteudoEducacional>()
    nomes.forEach( { it -> conteudos.add( ConteudoEducacional( it ) ) } )

    val conteudosBackend = mutableListOf( conteudos[0], conteudos[1], conteudos[2], conteudos[3] )
    val conteudosFrontend = mutableListOf( conteudos[0], conteudos[1], conteudos[4], conteudos[5] )
    val conteudosMobile = mutableListOf( conteudos[0], conteudos[1], conteudos[6], conteudos[7] )

    return mutableListOf(
            Formacao( "Formação backend", Nivel.BASICO, conteudosBackend ),
            Formacao( "Formação frontend", Nivel.BASICO, conteudosFrontend ),
            Formacao( "Formação mobile", Nivel.INTERMEDIARIO, conteudosMobile )
    )
}

fun main() {

    val ( formacaoBackend, formacaoFrontend, formacaoMobile ) = criarFormacoes()

    formacaoBackend.exibirDetalhes()
    formacaoFrontend.exibirDetalhes()
    formacaoMobile.exibirDetalhes()

    formacaoMobile.matricular( Usuario( "Ana", "ana@gmail.com" ) )
    formacaoBackend.matricular( Usuario( "Pedro", "pedrosa@gmail.com" ) )
    formacaoFrontend.matricular( Usuario( "Suzane", "susie@gmail.com" ) )
    formacaoBackend.matricular( Usuario( "Paulo", "paulo@gmail.com" ) )
    formacaoMobile.matricular( Usuario( "Isaque", "isaque@gmail.com" ) )

    println()

    formacaoBackend.exibirInscritos()
    formacaoFrontend.exibirInscritos()
    formacaoMobile.exibirInscritos()
}