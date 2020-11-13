import java.math.BigDecimal

/**
 *
 * @author Ege bircan
 * @param T  type of the
 */
/**
 *
 *
 * @author
 *
 */
fun main() {
	var a = 1

	// simple name in template:
	val s1 = "a is $a"

	s1.toIntOrNull()

	a = 2
	val s2 = "${s1.replace("is", "was")}, but now is $a"

	println(s2)

	val items = listOf("apple", "banana", "kiwifruit")

	for (index in items.indices) {
		println("item at $index is ${items[index]}")
	}

	for (i in 1..3 step 2) {
		println(i)
	}

	for (i in 6 downTo 0 step 2) {
		println(i)

		if (true) {
			println("deneme")
		}
	}

	val x = 10
	val y = 9

	if (x in 1..y + 1) {
		println("fits in range")
	}

	val list = listOf("a", "b", "c")

	if (2 !in 0..list.lastIndex) {
		println("-1 is out of range")
	}

	val fruits = listOf("banana", "avocado", "apple", "kiwifruit")

	fruits
		.filter { it.startsWith("a") }
		.sortedBy { it }
		.map { it.toUpperCase() }
		.forEach { println(it) }

	val map = mapOf("a" to 1, "b" to 2, "c" to 3)

	for ((k, v) in map) {
		println("$k -> $v")
	}

	val p: String by lazy {
		println("lazy computing. . .")
		"ege"
	}

	println(p)
	println(p)
	println(p)
	println(p)


	val ndeneme: String? = "egsd"

	println(ndeneme?.length ?: "empty string")
	// The comment starts here
	// /* contains a nested comment */
	// and ends here.


	fun calcTaxes(): BigDecimal = TODO("Waiting for feedback from accounting")
	//calcTaxes()

	ndeneme?.let { "deneme234" }

	println(ndeneme?.let {
		println("blokun içinde")
		"deneme234"
	}
	)

	val boolVal = false
	fun theAnswer() = if (boolVal) 4 + 2 else 31

	(println(theAnswer()))

	var ege1 = 1
	var ege2 = 2
	ege1 = ege2.also { ege2 = ege1 }

	println("$ege1, $ege2")

	val byteTest: Short = 32767
	val oneMillion = 1_000_000

	println(oneMillion)

	println(decimalDigitValue('9'))

	val byteArr: ByteArray = byteArrayOf(0x2E, 0x38)
	//byteArr[0] = byteArr[1] + byteArr[2]

	println(byteArr[0])

	val arr = ByteArray(5)
	arr.forEach { print("$it ") }

	println("")

	val arr2 = Array(5) { it }
	arr2.forEach { print("$it ") }

	println("")

	val intarr = IntArray(5) { it * 1 }
	intarr.forEach { print("$it ") }

	println("")

	val text = """
	>for (c in "foo")
	>   print(c)
	""".trimMargin(">")

	println(text)

	val price = """
	${'$'}ege
	"""

	println(price)

	val denemeName: String? = null
	//val s = denemeName ?: fail("Name required")
	//println(s)

	val xy = null
	if (xy is Nothing?) println("NULL")

	loop@ for (i in 1..20) {
		println(i)
		loop2@ for (j in 1..100) {
			if (j == 31) {
				println("")
				break@loop2
			}
			print("$j ")
		}
	}

	foo()

	println("")
	class Person {
		var name: String = "ege"
			get() = "qwert"
	}

	val deno = Person()
	deno.name = "bircan"
	println(deno.name)

	var allByDefault: Int? // error: explicit initializer required, default getter and setter implied

	val sum = { arg1: Int, arg2: Int -> arg1 + arg2 }
	println(sum(2, 3))

	val intArr: IntArray = intArrayOf(1, 2, 3, 4)
	println(intArr.get(0))
}

fun decimalDigitValue(c: Char): Int {
	if (c !in '0'..'9')
		throw IllegalArgumentException("Out of range")
	return c.toInt() - '0'.toInt() // Explicit conversions to numbers
}

fun fail(message: String): Nothing {
	throw IllegalArgumentException(message)
}

fun foo() {
	listOf(1, 2, 3, 4, 5).forEach {
		if (it == 3) return@forEach // local return to the caller of the lambda, i.e. the forEach loop
		print(it)
	}
	print(" done with explicit label")
}

open class Rectangle {
	open fun draw() { /* ... */ }
}

interface Polygon {
	fun draw() { /* ... */ } // interface members are 'open' by default
}

class Square() : Rectangle(), Polygon {
	// The compiler requires draw() to be overridden:
	override fun draw() {
		super<Rectangle>.draw() // call to Rectangle.draw()
		super<Polygon>.draw() // call to Polygon.draw()
	}
}

open class Shape {
	open fun draw() { /*...*/ }
	fun fill() { /*...*/ }
}

class Circle() : Shape() {
	override fun draw() { /*...*/ }
}

open class Polygon2 {
	open fun draw() {}
}

abstract class Rectangle2 : Polygon2() {
	abstract override fun draw()
}

interface Named {
	val name: String
}

interface Person : Named {
	val firstName: String
	val lastName: String

	override val name: String get() = "$firstName $lastName"
}

data class Employee(
	// implementing 'name' is not required
	override val firstName: String,
	override val lastName: String,
	val position: String
) : Person

