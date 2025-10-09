// package seedu.address.model.person;

// import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

// import java.util.Collections;
// import java.util.HashSet;
// import java.util.Objects;
// import java.util.Set;

// import seedu.address.commons.util.ToStringBuilder;
// import seedu.address.model.tag.Tag;

// /**
//  * Represents a Student in the address book.
//  * Guarantees:
//  * - Core identity fields (name, phone, email, class, subjects, emergency contact) are present and validated.
//  * - Optional fields (attendance, payment status, assignment status) may be absent or updated over time.
//  * - Identity fields are immutable
//  * - Operational fields are mutable to reflect real-time updates.
//  */
// public class Student {

//     // Identity fields
//     private final Name name;
//     private final Phone phone;
//     private final Email email;
//     private final StudentClss studentClass;
//     private final Subjects subjects;
//     private final EmergencyContact emergencyContact;

//     // Data fields
//     private Attendance attendance;
//     private PaymentStatus paymentStatus;
//     private AssignmentStatus assignmentStatus;

//     // Administrative fields
//     private boolean isArchived = false;

//     /**
//      * Every field must be present and not null.
//      */
//     public Student(Name name, Phone phone, Email email, studentClss studentClass, Subjects subjects,
//                    EmergencyContact emergencyContact, Attendance attendance,
//                    PaymentStatus paymentStatus, AssignmentStatus assignmentStatus) {
//         requireAllNonNull(name, phone, email, studentClass, subjects, emergencyContact);
//         this.name = name;
//         this.phone = phone;
//         this.email = email;
//         this.studentClass = studentClass;
//         this.subjects = subjects;
//         this.emergencyContact = emergencyContact;
//         this.attendance = attendance;
//         this.paymentStatus = paymentStatus;
//         this.assignmentStatus = assignmentStatus;
//     }

//     public Name getName() {
//         return name;
//     }

//     public Phone getPhone() {
//         return phone;
//     }

//     public Email getEmail() {
//         return email;
//     }

//     public StudentClss getStudentClass() {
//         return studentClass;
//     }

//     public Subjects getSubjects() {
//         return subjects;
//     }

//     public EmergencyContact getEmergencyContact() {
//         return emergencyContact;
//     }

//     public Attendance getAttendance() {
//         return attendance;
//     }

//     public PaymentStatus getPaymentStatus() {
//         return paymentStatus;
//     }

//     public AssignmentStatus getAssignmentStatus() {
//         return assignmentStatus;
//     }

//     public boolean isArchived() {
//         return isArchived;
//     }

//     public void setAttendance(Attendance attendance) {
//         this.attendance = attendance;
//     }

//     public void setPaymentStatus(PaymentStatus paymentStatus) {
//         this.paymentStatus = paymentStatus;
//     }

//     public void setAssignmentStatus(AssignmentStatus assignmentStatus) {
//         this.assignmentStatus = assignmentStatus;
//     }

//     public void setArchived(boolean isArchived) {
//         this.isArchived = isArchived;
//     }

//     /**
//      * Returns true if both Students have the same name.
//      * This defines a weaker notion of equality between two students.
//      */
//     public boolean isSameStudent(Student otherStudent) {
//         if (otherStudent == this) {
//             return true;
//         }

//         return otherStudent != null
//                 && otherStudent.getName().equals(getName());
//     }

//     /**
//      * Returns true if both Students have the same identity and data fields.
//      * This defines a stronger notion of equality between two Students.
//      */
//     @Override
//     public boolean equals(Object other) {
//         if (other == this) {
//             return true;
//         }

//         // instanceof handles nulls
//         if (!(other instanceof Student)) {
//             return false;
//         }

//         Student otherStudent = (Student) other;
//         return name.equals(otherStudent.name)
//                 && phone.equals(otherStudent.phone)
//                 && email.equals(otherStudent.email)
//                 && studentClass.equals(otherStudent.studentClass)
//                 && subjects.equals(otherStudent.subjects)
//                 && emergencyContact.equals(otherStudent.emergencyContact)
//                 && attendance.equals(otherStudent.attendance)
//                 && paymentStatus.equals(otherStudent.paymentStatus)
//                 && assignmentStatus.equals(otherStudent.assignmentStatus);
//     }

//     @Override
//     public int hashCode() {
//         // use this method for custom fields hashing instead of implementing your own
//         return Objects.hash(name, phone, email, studentClass, subjects, emergencyContact,
//                 attendance, paymentStatus, assignmentStatus);
//     }

//     @Override
//     public String toString() {
//         return new ToStringBuilder(this)
//                 .add("name", name)
//                 .add("phone", phone)
//                 .add("email", email)
//                 .add("class", studentClass)
//                 .add("subjects", subjects)
//                 .add("emergencyContact", emergencyContact)
//                 .add("attendance", attendance)
//                 .add("paymentStatus", paymentStatus)
//                 .add("assignmentStatus", assignmentStatus)
//                 .add("isArchived", isArchived)
//                 .toString();
//     }

// }
