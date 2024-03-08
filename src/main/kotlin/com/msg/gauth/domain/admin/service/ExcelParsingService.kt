package com.msg.gauth.domain.admin.service

import com.msg.gauth.domain.admin.exception.FileExtensionInvalidException
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.util.ExcelUtil
import org.apache.commons.compress.utils.FileNameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.tika.Tika
import org.springframework.web.multipart.MultipartFile

@TransactionalService
class ExcelParsingService(
    private val userRepository: UserRepository,
){
    fun execute(file: MultipartFile){
        val tika = Tika()
        val detect = tika.detect(file.bytes)
        val extension = FileNameUtils.getExtension(file.originalFilename)

        if(!ExcelUtil.isExcel(detect, extension))
            throw FileExtensionInvalidException()

        val workBook: Workbook =
            if (extension.equals("xlsx"))
                XSSFWorkbook(file.inputStream)
            else
                HSSFWorkbook(file.inputStream)

        val workSheet:Sheet = workBook.getSheetAt(0)
        val map = hashMapOf<String, UpdateDto>()

        for(i in 1 until workSheet.physicalNumberOfRows){
            val row = workSheet.getRow(i)
            val email = row.getCell(4).stringCellValue

            map[email] =
                UpdateDto(
                    name = row.getCell(3).stringCellValue.toString(),
                    grade = row.getCell(0).numericCellValue.toInt(),
                    classNum = row.getCell(1).numericCellValue.toInt(),
                    num = row.getCell(2).numericCellValue.toInt(),
                    gender = Gender.valueOf(row.getCell(5).stringCellValue)
                )
        }
        userRepository.findByEmailIn(map.keys.toList())
            .forEach {
                val updateDto = map[it.email] ?: throw Exception()

                userRepository.save(
                    User(
                        id = it.id,
                        email = it.email,
                        password = it.password,
                        gender = updateDto.gender,
                        name = updateDto.name,
                        grade = updateDto.grade,
                        classNum = updateDto.classNum,
                        num = updateDto.num,
                        roles = it.roles,
                        state = UserState.CREATED,
                        profileUrl = it.profileUrl
                    )
                )
            }
    }
    data class UpdateDto(
        val name: String,
        val grade: Int,
        val classNum: Int,
        val num: Int,
        val gender: Gender
    )
}