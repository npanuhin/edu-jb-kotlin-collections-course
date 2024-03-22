package org.jetbrains.kotlin.course.old.school.functions

import org.jetbrains.kotlin.course.old.school.photo.Color
import org.jetbrains.kotlin.course.old.school.photo.Accessory
import org.jetbrains.kotlin.course.old.school.photo.PhotoCharacter
import org.springframework.stereotype.Service

@Service
class GameFunctionsService {
    fun getAllPossibleColors(): List<String> = Color.entries.map { it.name.lowercase() }

    private fun String.toColor(): Color = Color.valueOf(this.replaceFirstChar{ it.titlecase() })

    private fun Iterable<String>.toPhotoCharacters(): List<PhotoCharacter> =
        this.map { PhotoCharacter.valueOf(it.replaceFirstChar{ char -> char.titlecase() }) }

    fun Iterable<String>.findPhoto(colorStr: String): PhotoCharacter? =
        toPhotoCharacters().find { it.backgroundColor == colorStr.toColor() }

    fun Iterable<String>.groupPhotosByColor(): List<PhotoCharacter> =
        toPhotoCharacters().groupBy { it.backgroundColor }.values.flatten()

    fun Iterable<String>.groupPhotosByHairAndHat(): List<PhotoCharacter> =
        toPhotoCharacters()
            .groupBy { it.hairType }.values
            .map {
                it.groupBy {
                    char -> char.accessories?.contains(Accessory.Hat) ?: false
                }
            }.flatMap { it.values }.flatten()
}
