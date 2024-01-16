package com.example.teach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Här skapar jag alla konstanter som kommer motsvara tabell/kolumn- namn

    public static final String TEACHER_TABLE = "TEACHER_TABLE";
    public static final String STUDENT_TABLE = "STUDENT_TABLE";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PROFILE_PICTURE = "PROFILE_PICTURE";

    public static final String SUBJECT_TABLE = "SUBJECT_TABLE";
    public static final String COLUMN_SUBJECT_ID = "SUBJECT_ID";
    public static final String COLUMN_NAME = "NAME";

    public static final String GRADE_TABLE = "GRADE_TABLE";
    public static final String COLUMN_GRADE_ID = "GRADE_ID";
    public static final String COLUMN_TYPE = "TYPE";

    public static final String TABLE_TEACHER_SUBJECT = "TABLE_TEACHER_SUBJECT";
    public static final String TABLE_STUDENT_SUBJECT = "TABLE_STUDENT_SUBJECT";

    public static final String TABLE_QUESTION = "TABLE_QUESTION";
    public static final String COLUMN_QUESTION_ID = "QUESTION_ID";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";

    public static final String TABLE_TEACHER_QUESTION = "TABLE_TEACHER_QUESTION";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ANSWER = "ANSWER";
    public static final String COLUMN_ANSWER_PICTURE = "ANSWER_PICTURE";
    public static final String SUBJECT_NAME_TABLE = "SUBJECT_NAME_TABLE";
    public static final String COLUMN_SUBJECT_NAME_ID = "COLUMN_SUBJECT_NAME_ID";
    public static final String COLUMN_TITLE = "COLUMN_TITLE";
    public static final String COLUMN_DATE_TIME = "COLUMN_DATE_TIME";

    //Databasens namn är teach.db
    public DataBaseHelper(Context context) {

        super(context, "teach.db", null, 1);
    }


    //Här skapar jag alla tabeller och sambandstabeller för databasen
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTeacher = "CREATE TABLE " + TEACHER_TABLE + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PROFILE_PICTURE + " BLOB)";

        String createTableStudent = "CREATE TABLE " + STUDENT_TABLE + " ("
                + COLUMN_USERNAME + " TEXT PRIMARY KEY, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PROFILE_PICTURE + " BLOB)";

        String createTableGrade = "CREATE TABLE " + GRADE_TABLE + " ("
                + COLUMN_GRADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TYPE + " TEXT)";

        String createTableSubjectName = "CREATE TABLE " + SUBJECT_NAME_TABLE + " ("
                + COLUMN_SUBJECT_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_IMAGE + " BLOB)";

        String createTableSubject = "CREATE TABLE " + SUBJECT_TABLE + " ("
                + COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SUBJECT_NAME_ID + " INTEGER, "
                + COLUMN_GRADE_ID + " INTEGER," +
                " FOREIGN KEY (" + COLUMN_GRADE_ID + ") REFERENCES " + GRADE_TABLE + "(" + COLUMN_GRADE_ID + ")," +
                " FOREIGN KEY (" + COLUMN_SUBJECT_NAME_ID + ") REFERENCES " + SUBJECT_NAME_TABLE + "(" + COLUMN_SUBJECT_NAME_ID + "))";

        String createTableTeacherSubject = "CREATE TABLE " + TABLE_TEACHER_SUBJECT + " ("
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_SUBJECT_ID + " INTEGER," +
                " PRIMARY KEY (" + COLUMN_USERNAME + ", " + COLUMN_SUBJECT_ID + "), " +
                " FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TEACHER_TABLE + "(" + COLUMN_USERNAME + ")," +
                " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + SUBJECT_TABLE + "(" + COLUMN_SUBJECT_ID + "))";

        String createTableStudentSubject = "CREATE TABLE " + TABLE_STUDENT_SUBJECT + " ("
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_SUBJECT_ID + " INTEGER," +
                " PRIMARY KEY (" + COLUMN_USERNAME + ", " + COLUMN_SUBJECT_ID + "), " +
                " FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + STUDENT_TABLE + "(" + COLUMN_USERNAME + ")," +
                " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + SUBJECT_TABLE + "(" + COLUMN_SUBJECT_ID + "))";

        String createTableQuestion = "CREATE TABLE " + TABLE_QUESTION + " ("
                + COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SUBJECT_ID + " INTEGER,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_IMAGE + " BLOB, "
                + COLUMN_DATE_TIME + " TEXT," +
                " FOREIGN KEY (" + COLUMN_SUBJECT_ID + ") REFERENCES " + SUBJECT_TABLE + "(" + COLUMN_SUBJECT_ID + ")," +
                " FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + STUDENT_TABLE + "(" + COLUMN_USERNAME + "))";

        String createTableTeacherQuestion = "CREATE TABLE " + TABLE_TEACHER_QUESTION + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_QUESTION_ID + " INTEGER, "
                + COLUMN_ANSWER + " TEXT,"
                + COLUMN_ANSWER_PICTURE + " BLOB, "
                + COLUMN_DATE_TIME + " TEXT," +
                " FOREIGN KEY (" + COLUMN_USERNAME + ") REFERENCES " + TEACHER_TABLE + "(" + COLUMN_USERNAME + ")," +
                " FOREIGN KEY (" + COLUMN_QUESTION_ID + ") REFERENCES " + TABLE_QUESTION + "(" + COLUMN_QUESTION_ID + "))";


        db.execSQL(createTableTeacher);
        db.execSQL(createTableStudent);
        db.execSQL(createTableGrade);
        db.execSQL(createTableSubjectName);
        db.execSQL(createTableSubject);
        db.execSQL(createTableTeacherSubject);
        db.execSQL(createTableStudentSubject);
        db.execSQL(createTableQuestion);
        db.execSQL(createTableTeacherQuestion);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }

    //Metod för att lägga till en lärare i databasen
    public boolean addTeacher(Teacher teacher) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, teacher.getUsername());
        cv.put(COLUMN_PASSWORD, teacher.getPassword());
        cv.put(COLUMN_EMAIL, teacher.geteMail());
        cv.put(COLUMN_PROFILE_PICTURE, teacher.convertImage());

        long created = db.insert(TEACHER_TABLE, null, cv);
        return created != -1;
    }

    //Metod för att lägga till en student i databasen
    public boolean addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, student.getUsername());
        cv.put(COLUMN_PASSWORD, student.getPassword());
        cv.put(COLUMN_EMAIL, student.geteMail());
        cv.put(COLUMN_PROFILE_PICTURE, student.convertImage());

        long created = db.insert(STUDENT_TABLE, null, cv);
        if (created == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    //Metod för att uppdatera profilbild för användare
    public boolean updateProfilePicture(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PROFILE_PICTURE, user.convertImage());

        if (user.getClass().getSimpleName().equals("Teacher")) {

            long success = db.update(TEACHER_TABLE, cv, COLUMN_USERNAME + " = ?", new String[]{user.getUsername()});
            return success != -1;

        } else {
            long success = db.update(STUDENT_TABLE, cv, COLUMN_USERNAME + " = ?", new String[]{user.getUsername()});
            return success != -1;

        }


    }

    //Metod för att hämta alla användare ur databasen
    public List<User> getUsers() {
        List<User> allUsersList = new ArrayList<>();

        String queryGetTeachers = "SELECT * FROM " + TEACHER_TABLE;
        String queryGetStudents = "SELECT * FROM " + STUDENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryGetTeachers, null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(0);
                String password = cursor.getString(1);
                String eMail = cursor.getString(2);
                byte[] bitmap = cursor.getBlob(3);
                Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);

                Teacher t = new Teacher(username, password, eMail, image);

                allUsersList.add(t);

            } while (cursor.moveToNext());

        }

        Cursor cursor2 = db.rawQuery(queryGetStudents, null);

        int i = cursor2.getCount();
        if (cursor2.moveToFirst()) {
            do {
                String username = cursor2.getString(0);
                String password = cursor2.getString(1);
                String eMail = cursor2.getString(2);
                byte[] bitmap = cursor2.getBlob(3);
                Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);

                Student s = new Student(username, password, eMail, image);

                allUsersList.add(s);

            } while (cursor2.moveToNext());

        }

        cursor.close();
        cursor2.close();
        db.close();
        return allUsersList;
    }

    //Metod för att hämta en students profilbild
    public Bitmap getStudentImage(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetStudentImage = "SELECT " + COLUMN_PROFILE_PICTURE + " FROM " + STUDENT_TABLE
                + " WHERE " + COLUMN_USERNAME + " =?";


        Cursor cursor = db.rawQuery(queryGetStudentImage, new String[]{username});
        Bitmap image = null;
        if (cursor.moveToFirst()) {

            byte[] bitmap = cursor.getBlob(0);
            image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);

        }

        cursor.close();
        db.close();
        return image;

    }

    //Metod för att hämta en lärares profilbild
    public Bitmap getTeacherImage(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetTeacherImage = "SELECT " + COLUMN_PROFILE_PICTURE + " FROM " + TEACHER_TABLE
                + " WHERE " + COLUMN_USERNAME + " =?";


        Cursor cursor = db.rawQuery(queryGetTeacherImage, new String[]{username});
        Bitmap image = null;
        if (cursor.moveToFirst()) {

            byte[] bitmap = cursor.getBlob(0);
            image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);

        }

        cursor.close();
        db.close();
        return image;

    }

    //Metod för att hämta ett ämnes "ämnes bild"
    public Bitmap getSubjectImage(int subjectId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetSubjectImage = "SELECT " + COLUMN_IMAGE + " FROM "
                + SUBJECT_TABLE + ", " + SUBJECT_NAME_TABLE + " WHERE "
                + COLUMN_SUBJECT_ID + "=? AND " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_NAME_ID
                + " = " + SUBJECT_NAME_TABLE + "." + COLUMN_SUBJECT_NAME_ID;

        Cursor cursor = db.rawQuery(queryGetSubjectImage, new String[]{Integer.toString(subjectId)});
        Bitmap image = null;
        if (cursor.moveToFirst()) {
            byte[] bitmap = cursor.getBlob(0);
            image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        }

        db.close();
        cursor.close();
        return image;
    }

    //Metod för att ta bort ett ämne för en användare
    public boolean removeSubjectForUser(User user, int subjectId) {

        SQLiteDatabase db = this.getWritableDatabase();
        if (user.getClass().getSimpleName().equals("Student")) {
            return db.delete(TABLE_STUDENT_SUBJECT, COLUMN_USERNAME + " =? AND " + COLUMN_SUBJECT_ID + " =?", new String[]{user.getUsername(), Integer.toString(subjectId)}) > 0;
        } else {
            return db.delete(TABLE_TEACHER_SUBJECT, COLUMN_USERNAME + " =? AND " + COLUMN_SUBJECT_ID + " =?", new String[]{user.getUsername(), Integer.toString(subjectId)}) > 0;
        }

    }

    //Metod för att lägga till ett ämne för en användare
    public boolean addSubjectForUser(User user, int subjectId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getUsername());
        cv.put(COLUMN_SUBJECT_ID, subjectId);
        if (user.getClass().getSimpleName().equals("Student")) {
            return db.insert(TABLE_STUDENT_SUBJECT, null, cv) > 0;
        } else {
            return db.insert(TABLE_TEACHER_SUBJECT, null, cv) > 0;
        }

    }

    //Metod för att hämta alla ämnen som en specifik användare har valt
    public ArrayList<SubjectForViews> getAllSubjects(User user) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubjectForViews> subList = new ArrayList<>();

        String queryGetSubjectsStudent = "SELECT " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE +
                " FROM " + TABLE_STUDENT_SUBJECT + ", " + SUBJECT_TABLE + ", " + GRADE_TABLE + ", " + SUBJECT_NAME_TABLE +
                " WHERE " + TABLE_STUDENT_SUBJECT + "." + COLUMN_USERNAME + " =?  AND "
                + TABLE_STUDENT_SUBJECT + "." + COLUMN_SUBJECT_ID + " = " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_ID + " AND "
                + GRADE_TABLE + "." + COLUMN_GRADE_ID + " = " + SUBJECT_TABLE + "." + COLUMN_GRADE_ID + " AND "
                + SUBJECT_NAME_TABLE + "." + COLUMN_SUBJECT_NAME_ID + " = " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_NAME_ID;

        String queryGetSubjectsTeacher = "SELECT " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE +
                " FROM " + TABLE_TEACHER_SUBJECT + ", " + SUBJECT_TABLE + ", " + GRADE_TABLE + ", " + SUBJECT_NAME_TABLE +
                " WHERE " + TABLE_TEACHER_SUBJECT + "." + COLUMN_USERNAME + " =?  AND "
                + TABLE_TEACHER_SUBJECT + "." + COLUMN_SUBJECT_ID + " = " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_ID + " AND "
                + GRADE_TABLE + "." + COLUMN_GRADE_ID + " = " + SUBJECT_TABLE + "." + COLUMN_GRADE_ID + " AND "
                + SUBJECT_NAME_TABLE + "." + COLUMN_SUBJECT_NAME_ID + " = " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_NAME_ID;

        Cursor cursor;
        if (user.getClass().getSimpleName().equals("Student")) {
            cursor = db.rawQuery(queryGetSubjectsStudent, new String[]{user.getUsername()});
        } else {
            cursor = db.rawQuery(queryGetSubjectsTeacher, new String[]{user.getUsername()});
        }


        if (cursor.moveToFirst()) {
            do {
                int subjectId = cursor.getInt(0);
                String subjectName = cursor.getString(1);
                String subjectAgeGroup = cursor.getString(2);
                byte[] byteArray = cursor.getBlob(3);
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                SubjectForViews sub = new SubjectForViews(subjectId, subjectName, subjectAgeGroup, image, true);

                subList.add(sub);


            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return subList;
    }

    //Metod för att hämta alla ämnen som finns i databasen
    public ArrayList<SubjectForViews> getAllSubjects() {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<SubjectForViews> subList = new ArrayList<SubjectForViews>();

        String queryGetSubjects = "SELECT " + COLUMN_SUBJECT_ID + ", " + COLUMN_NAME + ", " + COLUMN_TYPE + ", " + COLUMN_IMAGE +
                " FROM " + SUBJECT_TABLE + ", " + GRADE_TABLE + ", " + SUBJECT_NAME_TABLE +
                " WHERE " + GRADE_TABLE + "." + COLUMN_GRADE_ID + " = " + SUBJECT_TABLE + "." + COLUMN_GRADE_ID + " AND "
                + SUBJECT_NAME_TABLE + "." + COLUMN_SUBJECT_NAME_ID + " = " + SUBJECT_TABLE + "." + COLUMN_SUBJECT_NAME_ID;

        Cursor cursor = db.rawQuery(queryGetSubjects, null);

        if (cursor.moveToFirst()) {
            do {
                int subjectId = cursor.getInt(0);
                String subjectName = cursor.getString(1);
                String subjectAgeGroup = cursor.getString(2);
                byte[] byteArray = cursor.getBlob(3);
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                SubjectForViews sub = new SubjectForViews(subjectId, subjectName, subjectAgeGroup, image, false);

                subList.add(sub);


            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return subList;
    }

    //Metod för att lägga till en ny fråga i databasen
    public boolean addQuestion(Question question) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cv.put(COLUMN_SUBJECT_ID, question.getSubjectId());
        cv.put(COLUMN_USERNAME, question.getUsername());
        cv.put(COLUMN_TITLE, question.getTitle());
        cv.put(COLUMN_DESCRIPTION, question.getDescription());
        cv.put(COLUMN_IMAGE, question.convertImage());
        cv.put(COLUMN_DATE_TIME, dateFormat.format(question.getPostDate()));

        long created = db.insert(TABLE_QUESTION, null, cv);
        if (created == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    //Metod för att hämta alla frågor ur databasen som är i de ämnen som en användare valt
    public ArrayList<Question> getQuestionsForUsersSubjects(ArrayList<SubjectForViews> subList) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Question> questionList = new ArrayList<>();
        String queryGetQuestions = "SELECT * FROM " + TABLE_QUESTION;
        if (subList.size() > 0) {

            for (int i = 0; i < subList.size(); i++) {
                if (i == 0) {
                    queryGetQuestions += " WHERE " + COLUMN_SUBJECT_ID + " = " + subList.get(i).getSubjectID();
                } else {
                    queryGetQuestions += " OR " + COLUMN_SUBJECT_ID + " = " + subList.get(i).getSubjectID();
                }
            }

        } else {
            return questionList;
        }

        queryGetQuestions += " ORDER BY " + COLUMN_DATE_TIME + " DESC";

        Cursor cursor = db.rawQuery(queryGetQuestions, null);
        if (cursor.moveToFirst()) {
            do {
                int questionId = cursor.getInt(0);
                int subjectId = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                String username = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(5);
                Bitmap image = null;
                if (byteArray != null) {
                    image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(cursor.getString(6));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Question question = new Question(questionId, subjectId, title, description, username, image, date);
                questionList.add(question);

            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return questionList;
    }

    //Metod för att hämta alla frågor som en student frågat
    public ArrayList<Question> getQuestions(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Question> questionList = new ArrayList<>();

        String queryGetQuestions = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_USERNAME + " =? " + " ORDER BY " + COLUMN_DATE_TIME + " DESC";

        Cursor cursor = db.rawQuery(queryGetQuestions, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                int questionId = cursor.getInt(0);
                int subjectId = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                String questionUsername = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(5);
                Bitmap image = null;
                if (byteArray != null) {
                    image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(cursor.getString(6));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Question question = new Question(questionId, subjectId, title, description, questionUsername, image, date);
                questionList.add(question);

            } while (cursor.moveToNext());
        }


        db.close();
        cursor.close();
        return questionList;
    }

    //Metod för att hämta en specifik fråga ur databasen
    public Question getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String queryGetQuestions = "SELECT * FROM " + TABLE_QUESTION + " WHERE " + COLUMN_QUESTION_ID + " =? ";

        Cursor cursor = db.rawQuery(queryGetQuestions, new String[]{Integer.toString(id)});
        Question question = new Question();

        if (cursor.moveToFirst()) {

            int questionId = cursor.getInt(0);
            int subjectId = cursor.getInt(1);
            String title = cursor.getString(2);
            String description = cursor.getString(3);
            String questionUsername = cursor.getString(4);
            byte[] byteArray = cursor.getBlob(5);
            Bitmap image = null;
            if (byteArray != null) {
                image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            try {
                date = dateFormat.parse(cursor.getString(6));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            question = new Question(questionId, subjectId, title, description, questionUsername, image, date);


        }


        db.close();
        cursor.close();
        return question;

    }

    //Metod för att lägga till ett nytt svar till en fråga
    public boolean addAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        cv.put(COLUMN_USERNAME, answer.getUsername());
        cv.put(COLUMN_ANSWER_PICTURE, answer.convertImage());
        cv.put(COLUMN_QUESTION_ID, answer.getQuestionId());
        cv.put(COLUMN_ANSWER, answer.getAnswer());
        cv.put(COLUMN_DATE_TIME, dateFormat.format(answer.getAnswerDate()));

        long created = db.insert(TABLE_TEACHER_QUESTION, null, cv);
        if (created == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    //Metod för att hämta en lista av alla svar på en fråga
    public ArrayList<Answer> getAnswers(int questionId) {
        ArrayList<Answer> answerList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryGetQuestions = "SELECT * FROM " + TABLE_TEACHER_QUESTION + " WHERE " + COLUMN_QUESTION_ID + " =? ";

        Cursor cursor = db.rawQuery(queryGetQuestions, new String[]{Integer.toString(questionId)});

        if (cursor.moveToFirst()) {

            do {
                int answerId = cursor.getInt(0);
                String username = cursor.getString(1);
                int questionIdForAnswer = cursor.getInt(2);
                String answer = cursor.getString(3);
                byte[] byteArray = cursor.getBlob(4);
                Bitmap image = null;
                if (byteArray != null) {
                    image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(cursor.getString(5));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Answer answerToReturn = new Answer(answerId, username, questionIdForAnswer, answer, image, date);
                answerList.add(answerToReturn);


            } while (cursor.moveToNext());
        }

        return answerList;
    }

    //Metod för att hämta en lista av alla frågor som en lärare svarat på
    public ArrayList<Question> getAnsweredQuestions(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Question> questionList = new ArrayList<>();

        String queryGetQuestions = "SELECT DISTINCT " + TABLE_QUESTION + "." + COLUMN_QUESTION_ID + ", " + TABLE_QUESTION + "." + COLUMN_SUBJECT_ID +
                ", " + TABLE_QUESTION + "." + COLUMN_TITLE + ", " + TABLE_QUESTION + "." + COLUMN_DESCRIPTION + ", " +
                TABLE_QUESTION + "." + COLUMN_USERNAME + ", " + TABLE_QUESTION + "." + COLUMN_IMAGE + ", " + TABLE_QUESTION + "." + COLUMN_DATE_TIME +
                " FROM " + TABLE_QUESTION + ", " + TABLE_TEACHER_QUESTION +
                " WHERE " + TABLE_TEACHER_QUESTION + "." + COLUMN_USERNAME + " =? " + " AND " + TABLE_QUESTION + "." + COLUMN_QUESTION_ID + " = " + TABLE_TEACHER_QUESTION + "." + COLUMN_QUESTION_ID +
                " ORDER BY " + TABLE_QUESTION + "." + COLUMN_DATE_TIME + " DESC";

        Cursor cursor = db.rawQuery(queryGetQuestions, new String[]{username});

        if (cursor.moveToFirst()) {
            do {
                int questionId = cursor.getInt(0);
                int subjectId = cursor.getInt(1);
                String title = cursor.getString(2);
                String description = cursor.getString(3);
                String questionUsername = cursor.getString(4);
                byte[] byteArray = cursor.getBlob(5);
                Bitmap image = null;
                if (byteArray != null) {
                    image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(cursor.getString(6));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Question question = new Question(questionId, subjectId, title, description, questionUsername, image, date);
                questionList.add(question);

            } while (cursor.moveToNext());
        }


        db.close();
        cursor.close();
        return questionList;
    }

    //Metod för att hårdkodat lägga till alla åldersrupper som ska finnas för ämnen
    public void createHardcodedGrades(Subject sub) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, GRADE_TABLE);

        if (count == 0) {
            cv.put(COLUMN_TYPE, sub.getAgeGroups()[0]);
            db.insert(GRADE_TABLE, null, cv);
            cv.put(COLUMN_TYPE, sub.getAgeGroups()[1]);
            db.insert(GRADE_TABLE, null, cv);
            cv.put(COLUMN_TYPE, sub.getAgeGroups()[2]);
            db.insert(GRADE_TABLE, null, cv);
        }


    }

    //Metod för att hårdkodat lägga till alla ämnens namn och bilderna som hör till ämnet
    public void createHardcodedSubjectNames(List<Subject> subjectList) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, SUBJECT_NAME_TABLE);

        if (count == 0) {
            for (Subject sub : subjectList) {
                cv.put(COLUMN_NAME, sub.getName());
                cv.put(COLUMN_IMAGE, sub.convertImage());

                db.insert(SUBJECT_NAME_TABLE, null, cv);
            }
        }
    }

    //Metod för att lägga till alla ämnen och göra kopplingarna till dess namn och åldersgrupp
    public void createHardcodedSubjects(List<Subject> subjectList) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, SUBJECT_TABLE);

        if (count == 0) {
            for (int i = 0; i < subjectList.size(); i++) {
                for (int j = 0; j < subjectList.get(i).getAgeGroups().length; j++) {
                    cv.put(COLUMN_SUBJECT_NAME_ID, i + 1);
                    cv.put(COLUMN_GRADE_ID, j + 1);
                    db.insert(SUBJECT_TABLE, null, cv);
                }
            }
        }
    }


}
