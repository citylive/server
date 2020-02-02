package com.citylive.server.service;


import com.citylive.server.MTree.Planar.MTree2D;
import com.citylive.server.MTree.common.Data;
import com.citylive.server.MTree.common.MTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Arrays;


@RestController
@RequestMapping("/testservice")
public class TestService {

    @Autowired
    MTree2D mtree;

    @RequestMapping(method = RequestMethod.GET, path = "/ul")
    public ResponseEntity uloc(){
        String ans = "";
        Data origin = new Data("gaurav",12.927460,77.671253);
        Data data1 = new Data("radhe",12.924867,77.671934);
        Data data2 = new Data("rashi",12.941135,77.696479);
        Data data3 = new Data("smdp",12.938285,77.698982);
        Data data4 = new Data("kriti",26.462850,80.362415);
        Data data5 = new Data("ny",40.705296,-73.902777);
        mtree.add(data1);
        mtree.add(data2);
        mtree.add(data3);
        mtree.add(data4);
        mtree.add(data5);
        ans += "\n------1 ALL------\n"+ mtree.getNearestAsList(origin).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
//        Data rd = new Data("u2",0.5,0.5);
//        mtree.add(rd);
        ans += "\n------2 2KM 10 max------\n"+ mtree.getNearestAsList(origin,2.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------3 100KM 2 max------\n"+ mtree.getNearestAsList(origin,100.0,2).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------2 5KM 10 max------\n"+ mtree.getNearestAsList(origin,5.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------2 7KM 10 max------\n"+ mtree.getNearestAsList(origin,7.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------2 15KM 10 max------\n"+ mtree.getNearestAsList(origin,15.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------2 1500KM 10 max------\n"+ mtree.getNearestAsList(origin,1500.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);
        ans += "\n------2 15000KM 10 max------\n"+ mtree.getNearestAsList(origin,15000.0,10).stream().map(d->d.data.toString()).reduce((a,b)->a+"\n"+b);

        System.out.println(ans);
        return ResponseEntity.ok(ans);
    }

    @RequestMapping("/hi")
    public ResponseEntity hi(){
        return ResponseEntity.ok("Hi! Welcome To Citylive");
    }

}
