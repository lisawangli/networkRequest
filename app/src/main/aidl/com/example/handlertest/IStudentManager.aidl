// IStudentManager.aidl
package com.example.handlertest;

// Declare any non-default types here with import statements
import com.example.handlertest.Student;
interface IStudentManager {

List<Student> getStudentList();

void addStudent(in Student student);
}
