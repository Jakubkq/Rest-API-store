package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.repository.GroupRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupTest {


    @Autowired
    private GroupRepository groupRepository;

    String GROUP_NAME_1 = "Owoce";
    String GROUP_NAME_2 = "Warzywa";
    String GROUP_NAME_3 = "Nabiał";
    String GROUP_NAME_4 = "Drogeria";

    @Test
    public void testShowAllGroups() {
        //Given
        List<Group> result = groupRepository.findAll();

        //Then
        assertTrue(result.isEmpty());

        //Cleanup
        groupRepository.deleteAll();
    }


    @Test
    public void testAddGroup() {
        //Given
        Group group1 = new Group(GROUP_NAME_1);
        Group group2 = new Group(GROUP_NAME_2);
        Group group3 = new Group(GROUP_NAME_3);
        Group group4 = new Group(GROUP_NAME_4);

        //When
        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);
        groupRepository.save(group4);

        //Then
        assertEquals(4, groupRepository.findAll().size());
        //Cleanup
        groupRepository.deleteAll();

    }
    @Test
    public void getGroupByIdTest() {
        //Given
        Group group1 = new Group(GROUP_NAME_1);
        Group group2 = new Group(GROUP_NAME_2);

        //When
        groupRepository.save(group1);
        groupRepository.save(group2);
        Long id = group1.getId();
        Optional<Group> result = groupRepository.findById(id);

        //Then
        assertTrue(result.isPresent());
        assertEquals(id,result.get().getId());
        System.out.println(group1);
        System.out.println(group2);

        //Cleanup
        groupRepository.deleteAll();
    }

    @Test
    public void updateSelectedGroupTest() {
        //Given
        Group group1 = new Group(GROUP_NAME_1);
        Group group2 = new Group(GROUP_NAME_2);

        //When
        groupRepository.save(group1);
        groupRepository.save(group2);
        Group editedGroup = groupRepository.findById(group1.getId()).get();
        editedGroup.setName(GROUP_NAME_3);
        groupRepository.save(editedGroup);

        //Then
        Assert.assertEquals(groupRepository.findById(group1.getId()).get().getName(),GROUP_NAME_3);
        assertEquals(2, groupRepository.findAll().size());

        //Cleanup
        groupRepository.deleteAll();
    }
    @Test
    public void DeleteGroupByIdTest() {
        //Given
        Group group1 = new Group(GROUP_NAME_1);
        Group group2 = new Group(GROUP_NAME_2);
        Group group3 = new Group(GROUP_NAME_3);

        //When
        groupRepository.save(group1);
        groupRepository.save(group2);
        groupRepository.save(group3);
        //assertEquals(3, groupRepository.findAll().size());
        groupRepository.deleteById(group3.getId());

        //Then
        assertEquals(2, groupRepository.findAll().size());

        //Cleanup
        groupRepository.deleteAll();
    }

}