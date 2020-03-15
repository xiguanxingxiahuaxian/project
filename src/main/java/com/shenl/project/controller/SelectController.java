package com.shenl.project.controller;

import com.shenl.project.bean.Goods;
import com.shenl.project.bean.SelectCore;
import com.shenl.project.bean.codeAndMegDTO;
import com.shenl.project.respository.GoodsRepository;
import com.shenl.project.respository.SelectCoreRepository;
import com.shenl.project.utils.CosUtils;
import com.shenl.project.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class SelectController {
    @Autowired
    SelectCoreRepository repository;

    codeAndMegDTO codeAndMegDTO;

    @RequestMapping(value = "/getSelectorList", method = RequestMethod.GET)
    public List<SelectCore> getSelectList() {
        List<SelectCore> list = repository.findAll();
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                String url = list.get(i).getGoods_url();
                url = CosUtils.GeneratePresignedUrl(url);
                list.get(i).setGoods_url(url);
            }
        }
        return list;
    }

    @RequestMapping(value = "/deleteSelector", method = RequestMethod.POST)
    public codeAndMegDTO getSelectList(int id) {
        codeAndMegDTO = new codeAndMegDTO();
        repository.delete(id);
        SelectCore objuect = repository.findOne(id);
        if(objuect==null){
            codeAndMegDTO.setCode("0");
            codeAndMegDTO.setMeg("删除成功");
        }else {
            codeAndMegDTO.setCode("-1");
            codeAndMegDTO.setMeg("删除失败");
        }
        return codeAndMegDTO;
    }
    /**
     *   首页由管理员固定来维护
     *   该管理员用户admins
     */

  @RequestMapping(value = "/saveSelectCore", method = RequestMethod.POST)
  public codeAndMegDTO saveSelectCore(
          @RequestParam("goods_name") String goods_name,
          @RequestParam("type") int type,
          @RequestParam("describe") String describe,
          @RequestParam("goods_price") String goods_price,
          @RequestParam("content") String content,
          @RequestParam("file") MultipartFile file) {

      String key = null;
      key = "/pay/room/admins/" + file.getOriginalFilename();
      long size=file.getSize();
      SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String time = sf.format(new Date());

      InputStream stream;
      codeAndMegDTO = new codeAndMegDTO();
      try {
          stream = file.getInputStream();
          String result = CosUtils.upLoadCos(stream, key, size);
          if (result.equals("success")) {
              SelectCore selectCore =new SelectCore();
              selectCore.setGoods_type(type);
              selectCore.setGoods_content(content);
              selectCore.setGoods_describe(describe);
              selectCore.setGoods_name(goods_name);
              selectCore.setGoods_price(goods_price);
              selectCore.setGoods_url(key);

              SelectCore goods = repository.save(selectCore);
              if (goods != null) {
                  codeAndMegDTO.setCode("0");
                  codeAndMegDTO.setMeg("用户提交成功");
                  return codeAndMegDTO;
              } else {
                  codeAndMegDTO.setCode("1");
                  codeAndMegDTO.setMeg("用户提交失败");
              }
          } else {
              codeAndMegDTO.setCode("1");
              codeAndMegDTO.setMeg("用户提交失败");
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      return codeAndMegDTO;
  }

}
