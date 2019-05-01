package org.kylin.service.xcode.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.kylin.bean.p2.XCodePair;
import org.kylin.bean.p2.XCodeReq;
import org.kylin.bean.p5.WCode;
import org.kylin.service.exporter.impl.XCode2DKillerDocExporter;
import org.kylin.service.xcode.XCodeService;
import org.kylin.service.xcode.filters.impl.BoldCodeFilter;
import org.kylin.service.xcode.filters.impl.GossipSimpleFilterr;
import org.kylin.service.xcode.filters.impl.InverseSelectCodeFilter;
import org.kylin.service.xcode.filters.impl.KdSimpleFilter;
import org.kylin.util.WCodeUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class XCodeServiceImpl implements XCodeService {

    @Override
    public List<WCode> quibinaryEncode(List<Set<Integer>> riddles) {

        int[] stat = new int[100];

        List<WCode> wCodes = Lists.newArrayListWithExpectedSize(100);

        for(int i=0; i<riddles.size()-1; i++ ){
            for(int j=i+1; j<riddles.size(); j++){
                Set<Integer> aSet = riddles.get(i);
                Set<Integer> bSet = riddles.get(j);
                for(Integer a: aSet)
                    for(Integer b: bSet){
                        int max = Math.max(a, b);
                        int min = Math.min(a, b);
                        int idx = min * 10 + max;
                        if(stat[idx] == 0){
                            wCodes.add(new WCode(2, min, max).setFreq(1));
                            stat[idx] = 1;
                        }
                    }
            }
        }

        Collections.sort(wCodes);

        return wCodes;
    }


    @Override
    public List<WCode> killCodes(XCodeReq req) {

        if(Objects.isNull(req)){
            return Collections.emptyList();
        }

        List<WCode> target = req.getwCodes();
        int count = CollectionUtils.size(target);
        log.info("杀码前 {} 注2D", count);

        if(CollectionUtils.isNotEmpty(target) && StringUtils.isNotBlank(req.getBoldCodeSeq())){
            target  = new BoldCodeFilter().filter(target, req.getBoldCodeSeq());
            log.info("胆杀 {} 注2D", (count - CollectionUtils.size(target)));
            count = CollectionUtils.size(target);
        }

        if(CollectionUtils.isNotEmpty(target) && StringUtils.isNotBlank(req.getInverseCodeSeq())){
            target = new InverseSelectCodeFilter().filter(target, req.getInverseCodeSeq());
            log.info("筛选杀 {} 注2D", (count - CollectionUtils.size(target)));
            count = CollectionUtils.size(target);
        }

        if(CollectionUtils.isNotEmpty(target) && StringUtils.isNotBlank(req.getGossipCodeSeq())){
            target = new GossipSimpleFilterr().filter(target, req.getGossipCodeSeq());
            log.info("二码杀 {} 注2D", (count - CollectionUtils.size(target)));
            count = CollectionUtils.size(target);
        }


        if(CollectionUtils.isNotEmpty(target)
                && StringUtils.isNotBlank(req.getKdSeq())){
            target = new KdSimpleFilter().filter(target, req.getKdSeq());
            log.info("跨度杀 {} 注2D", (count - CollectionUtils.size(target)));
            count = CollectionUtils.size(target);
        }


        log.info("杀码后 {} 注2D", count);

        return target;
    }

    @Override
    public List<WCode> compSelectCodes(XCodeReq req) {

        if(Objects.isNull(req) || CollectionUtils.isEmpty(req.getxCodePairs())){
            return Collections.emptyList();
        }

        List<List<WCode>> wCodesArray = new ArrayList<>();
        for(XCodePair pair : req.getxCodePairs()){
            wCodesArray.add(pair.getwCodes());
        }

        return WCodeUtils.merge(wCodesArray);
    }

    public static void main(String[] args) {
        List<Set<Integer>> params = new ArrayList<>();
        params.add(new HashSet<>(Arrays.asList(1)));
        params.add(new HashSet<>(Arrays.asList(2)));
        params.add(new HashSet<>(Arrays.asList(3)));
        params.add(new HashSet<>(Arrays.asList(4)));

        List<WCode> wCodes = new XCodeServiceImpl().quibinaryEncode(params);

        for(WCode wCode : wCodes){
            System.out.println(wCode);
        }
    }

    @Override
    public Optional<String> exportWCodeToFile(XCodeReq xCodeReq) {

        // 策略导出
        XCode2DKillerDocExporter exporter = new XCode2DKillerDocExporter(new XWPFDocument(), xCodeReq);
        try {
            exporter.init();
            exporter.writeDocHeader("我要发·定位2D法");
            exporter.writeBody();
            String fileName = exporter.exportCodes();
            return Optional.of(fileName);
        } catch (IOException e) {
            log.warn("导出文件错误", e);
            return Optional.empty();
        } catch (RuntimeException re){
            log.warn("导出文件错误", re);
            return Optional.empty();
        }

    }
}
