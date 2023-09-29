package com.example.demo

import com.example.demo.ui.util.toBigDecimalV1
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * [출처](https://webcache.googleusercontent.com/search?q=cache:zAqJzhOFb0cJ:https://madplay.github.io/post/the-need-for-bigdecimal-in-java+&cd=1&hl=ko&ct=clnk&gl=kr)
 */
class BigDecimalUnitTest {

    @Test
    fun `부동 소수점 방식의 오차`() {

        val price1 = 12.23
        val price2 = 34.45
        //46.68 ???
        println("결과는 ${price1.plus(price2)}")
        //46.68000000000001가 출력

        //근사한 차이지만, 금융 관련 프로그램에서는 이 오차가 큰 영향을 미칠 수 있기 때문에 주의해야 한다.
        //그러면 이 문제를 어떻게 해결할 수 있을까?
    }

    @Test
    fun `해결책은 BigDecimal_선언방법`() {
        //이러한 부동 소수점 표현 방식의 오차를 해결하기 위해 자바에서는 BigDecimal 클래스를 제공하고 있다.
        //소수점을 다루는 연산을 한다면 BigDecimal 클래스의 사용은 필수적이다.

        val price1 = BigDecimal("12.23")        //올바른 사용예시
        val price2 = BigDecimal.valueOf(34.45)  //올바른 사용예시

        // 아래와 같이 사용하면 안 된다.
        //val badCase = BigDecimal(12.23)           //잘못된 사용예시
        // 12.230000000000000426325641456060111522674560546875
    }

    @Test
    fun `BigDecimal_사칙연산_더하기(add)`() {
        val price1 = BigDecimal("12.23")
        val price2 = BigDecimal.valueOf(34.45)

        println("결과는 ${price1.add(price2)}")
    }

    @Test
    fun `BigDecimal_사칙연산_빼기(subtract)`() {
        // add 메서드로 덧셈 연산을 할 수 있다.
        // 예제에서 사용한 BigDecimal.ONE은 BigDecimal 클래스에서 제공하는 전역 상수다. ZERO, TEN 도 있다.
        val price = BigDecimal("12.23")
        // 2.23
        println("결과는 ${price.subtract(BigDecimal.TEN)}")
    }

    @Test
    fun `BigDecimal_사칙연산_곱하기(multiply)`() {

        val price = BigDecimal("1")
        // 10
        println("결과는 ${price.multiply(BigDecimal.TEN)}")
    }

    @Test
    fun `BigDecimal_사칙연산_나누기(divide)_Exception)`() {

        val price1 = BigDecimal("11")
        val price2 = BigDecimal.valueOf(3)
        // Exception in thread "main" java.lang.ArithmeticException:
        // Non-terminating decimal expansion; no exact representable decimal result.
        println("결과는 ${price1.divide(price2)}")

        // divide 메서드를 사용하면 나눗셈 연산이 가능하다.
        // 하지만 정확하게 나누어 몫이 떨어지지 않는 수의 경우 ArithmeticException 예외를 던진다.
        // 아무리 BigDecimal 클래스라도 나누어떨어지지 않는 수는 정확하게 표현할 수 없다.
        // 따라서 divide 메서드를 사용할 때는 소수점 몇 번째짜리까지, 어떻게 처리할 것인지 지정을 해줘야 한다.
    }

    @Test
    fun `BigDecimal_사칙연산_나누기(divide)`() {

        val price1 = BigDecimal("11")
        val price2 = BigDecimal.valueOf(3)

        /**
         * scale : N 번째 자리까지 표현
         * roundingMode : 처리방식 : RoundingMode.HALF_UP > 소수점 3번째 자리에서 반올림
         */
        val result = price1.divide(price2, 2, RoundingMode.HALF_UP)
        println("결과는 $result")
    }

    @Test
    fun `BigDecimal_사칙연산_나누기(reminder)_나머지 구하기`() {

        val price1 = BigDecimal("10")
        val price2 = BigDecimal.valueOf(4)

        val result = price1.remainder(price2)

        // 2
        println("결과는 $result")
    }

    @Test
    fun `BigDecimal_값_비교(compareTo)`() {

        val price = BigDecimal("10")

        // 같으면 0
        val result1 = price.compareTo(BigDecimal.TEN)

        // 크면 1
        val result2 = price.compareTo(BigDecimal.ONE)

        // 작으면 -1
        val result3 = BigDecimal.ONE.compareTo(price)

        println("result1 결과는 $result1")
        println("result2 결과는 $result2")
        println("result3 결과는 $result3")
    }

    @Test
    fun `BigDecimal_최대_최소(max, min)`() {

        val price = BigDecimal("10")

        // 10
        println("최대 값 : ${price.max(BigDecimal.ONE)}")
        // 1
        println("최소 값 : ${price.min(BigDecimal.ONE)}")
    }

    @Test
    fun `BigDecimal_소수점_처리_방식`() {
        println("================================")
        // 반올림(5이상 올림, 5미만 버림)
        // 소수점 첫 번째까지 표현, 두번째 자리에서 반올림
        // 12.4
        println("HALF_UP " + BigDecimal.valueOf(12.35).setScale(1, RoundingMode.HALF_UP))

        // 반올림(6이상 올림 6미만 버림)
        // 12.4
        println("HALF_DOWN " + BigDecimal.valueOf(12.45).setScale(1, RoundingMode.HALF_DOWN))

        // 12.6
        println("HALF_DOWN " + BigDecimal.valueOf(12.56).setScale(1, RoundingMode.HALF_DOWN))

        // 반올림(반올림 자리의 값이 짝수면 HALF_DOWN, 홀수면 HALF_UP)
        // 12.4 HALF_DOWN
        println("HALF_EVEN " + BigDecimal.valueOf(12.45).setScale(1, RoundingMode.HALF_EVEN))

        // 12.5 HALF_DOWN
        println("HALF_EVEN " + BigDecimal.valueOf(12.46).setScale(1, RoundingMode.HALF_EVEN))

        // 12.6 HALF_UP
        println("HALF_EVEN " + BigDecimal.valueOf(12.56).setScale(1, RoundingMode.HALF_EVEN))

        // 소수점 이하 모두 제거하고 올림
        // 13
        println("CEILING " + BigDecimal.valueOf(12.34).setScale(0, RoundingMode.CEILING))

        // 음수인 경우는 특정 자릿수 이하 제거
        // -12.3
        println("CEILING " + BigDecimal.valueOf(-12.34).setScale(1, RoundingMode.CEILING))

        // 특정 자릿수 이하 버림
        // 12.3
        println("FLOOR " + BigDecimal("12.37").setScale(1, RoundingMode.FLOOR))
    }

    @Test
    fun `isFinite 를 사용하면 Infinity 와 NaN 을 모두 체크`() {

        val min = Double.MIN_VALUE
        val max = Double.MAX_VALUE
        val positiveInfinity = Double.POSITIVE_INFINITY //양의 무한대 값을 유지하는 상수
        val negativeInfinity = Double.NEGATIVE_INFINITY //음의 무한대 값을 유지하는 상수
        val nan = Double.NaN

        println("min : $min")
        println("max : $max")
        println("positiveInfinity : $positiveInfinity")
        println("negativeInfinity : $negativeInfinity")
        println("nan : $nan")

        // 양의 무한대 예시
        val positiveInfinityTest1 = max + max
        println("positiveInfinityTest1 : $positiveInfinityTest1")

        // 음의 무한대 예시
        val negativeInfinityTest1 = -max -max
        println("negativeInfinityTest1 : $negativeInfinityTest1")

        // isNaN : Not a Number 예시
        val nanResult1 = 0.0 / 0.0
        val nanResult2 = Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY
        val nanResult3 = Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY


        println("0.0 / 0.0 = $nanResult1")
        println("Double.POSITIVE_INFINITY - Double.POSITIVE_INFINITY = $nanResult2")
        println("Double.NEGATIVE_INFINITY + Double.POSITIVE_INFINITY = $nanResult3")

        //그래서? isFinite 를 사용하면 유한한 숫자인지 체크할 수 있다. Infinity 와 NaN 을 And 조건으로 체크
        println("유한한 숫자인가? Infinity isFinite ${positiveInfinityTest1.isFinite()}")
        println("유한한 숫자인가? Nan isFinite : ${nanResult1.isFinite()}")

        //toBigDecimalV1 확장함수 사용
        println("positiveInfinityTest1 값을 출력 합니다. ${positiveInfinityTest1.toBigDecimalV1()}")
    }

    @Test
    fun `무한한 수를 BigDecimal로 변환시`(){
        //확장함수 구현을 통해 예외를 처리한 경우
        val nanResult1 = Double.POSITIVE_INFINITY.toBigDecimalV1()
//        val nanResult2 = Double.POSITIVE_INFINITY.toBigDecimal()
        println("$nanResult1")
//        println("예외!!!!! $nanResult2")
    }

    @Test
    fun `BigDecimal formatter 사용 예시`(){
        val number = BigDecimal("1234567890.123456789")
        val formatter = DecimalFormat("#,###.##")
        val formattedNumber = formatter.format(number)

        println("Formatted number: $formattedNumber")
    }
}