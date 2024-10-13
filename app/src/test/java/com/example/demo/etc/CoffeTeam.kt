package com.example.demo.etc

fun main() {
    // 팀 A와 팀 B의 원소들
    val teamA = listOf(1, 2, 3, 4, 6, 7, 8)
    val teamB = listOf("A", "B", "C", "D", "E", "F", "G", "H")

    // 사용자로부터 분배할 팀의 수 n 입력 받기
    println("몇 개의 팀으로 나누시겠습니까? ")
    val n = readLine()?.toIntOrNull() ?: return println("유효한 숫자를 입력하세요.")

    // 각 팀을 빈 리스트로 초기화
    val dividedTeams = List(n) { mutableListOf<Any>() }

    // A 팀과 B 팀을 각각 섞기 (랜덤 배치)
    val shuffledTeamA = teamA.shuffled()
    val shuffledTeamB = teamB.shuffled()

    // A 팀과 B 팀의 요소를 균등하게 분배
    shuffledTeamA.forEachIndexed { index, element ->
        dividedTeams[index % n].add(element)
    }
    shuffledTeamB.forEachIndexed { index, element ->
        dividedTeams[index % n].add(element)
    }

    // 결과 출력
    dividedTeams.forEachIndexed { index, team ->
        println("팀 ${index + 1}: $team")
    }
}