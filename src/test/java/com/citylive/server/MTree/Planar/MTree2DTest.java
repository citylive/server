package com.citylive.server.MTree.Planar;

import com.citylive.server.MTree.common.Data;
import com.citylive.server.MTree.common.MTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MTree2DTest {

    private MTree mtree ;

    @BeforeEach
    public void init(){
        mtree = new MTree2D();
        mtree.add(new Data("user1",0,0));
        mtree.add(new Data("user2",1,2));
        mtree.add(new Data("user3",-2,-2));
        mtree.add(new Data("user4",5,5));
        mtree.add(new Data("user2",2,6));
        mtree.add(new Data("user3",-5,1));
    }

    @Test
    public void test01() {
        MTree.Query res = mtree.getNearest(new Data(0.5,0.5),5,10);
        int count = 0;
        for(Object ri : res){
            count++;
        }

        res.iterator().forEachRemaining(ritem->{
            System.out.println(((MTree.ResultItem)ritem).data.toString() +" "+((MTree.ResultItem)ritem).distance);
        });
        assertEquals(count,3);
    }


    @Test public void test02() {
        MTree.Query res = mtree.getNearest(new Data(0,0),5,2);
        int count = 0;
        for(Object ri : res){
            count++;
        }

        res.iterator().forEachRemaining(ritem->{
            System.out.println(((MTree.ResultItem)ritem).data.toString() +" "+((MTree.ResultItem)ritem).distance);
        });
        assertEquals(count,2);
    }

    @Test public void test03() {
        MTree.Query res = mtree.getNearest(new Data(0,0),2.414,5);
        int count = 0;
        for(Object ri : res){
            count++;
        }

        res.iterator().forEachRemaining(ritem->{
            System.out.println(((MTree.ResultItem)ritem).data.toString() +" "+((MTree.ResultItem)ritem).distance);
        });
        assertEquals(count,2);
    }

    @Test public void test04() {
        mtree.remove(new Data(0,0));
        MTree.Query res = mtree.getNearest(new Data(0,0),2.414,5);
        int count = 0;
        for(Object ri : res){
            count++;
        }

        res.iterator().forEachRemaining(ritem->{
            System.out.println(((MTree.ResultItem)ritem).data.toString() +" "+((MTree.ResultItem)ritem).distance);
        });
        assertEquals(count,1);
    }

    @Test public void test05() {
        mtree.remove(new Data(0,0));
        mtree.remove(new Data(2,1));
        MTree.Query res = mtree.getNearest(new Data(0,0),2.414,5);
        int count = 0;
        for(Object ri : res){
            count++;
        }

        res.iterator().forEachRemaining(ritem->{
            System.out.println(((MTree.ResultItem)ritem).data.toString() +" "+((MTree.ResultItem)ritem).distance);
        });
        assertEquals(count,1);
    }
}