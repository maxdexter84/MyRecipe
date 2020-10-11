package com.maxdexter.myrecipe.ui


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.maxdexter.myrecipe.database.firestore.RemoteDataProvider
import com.maxdexter.myrecipe.database.room.AppDatabase
import com.maxdexter.myrecipe.database.room.NoteDao
import com.maxdexter.myrecipe.model.Recipe
import com.maxdexter.myrecipe.repository.NoteRepository
import com.maxdexter.myrecipe.ui.notelist.NoteListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test






class RecipeListViewModelTest {
    //правило JUnit для тестирования ViewModel:
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private val database: AppDatabase = mockk<AppDatabase>()
    private val remoteDataProvider: RemoteDataProvider = mockk<RemoteDataProvider>()
    private val noteDao: NoteDao = mockk<NoteDao>()
    private val mockRepository: NoteRepository = mockk()
    private val mockLifecycle: LifecycleOwner = mockk<LifecycleOwner>()
    private val notesLiveData = MutableLiveData<List<Recipe>>()
    private lateinit var viewModel: NoteListViewModel



    @Before
    fun setUp() {
        every {noteDao.getAllNote()} returns notesLiveData
       viewModel = NoteListViewModel(mockRepository, mockLifecycle)
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) {noteDao.getAllNote() }
    }

    @Test
    fun `should return notes`(){
        var result: List<Recipe>? = null
        val testData = listOf(Recipe(1), Recipe(2))
        viewModel.notes?.observe(mockLifecycle, {
            result = it
        })
        notesLiveData.value = testData
        assertEquals(testData, result)
    }



    @Test
    fun `should remove observer`(){
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }
}