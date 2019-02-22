package net.floodlightcontroller.QoSEvaluation;

import net.floodlightcontroller.MyLog;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.util.AppCookie;
import org.projectfloodlight.openflow.protocol.OFFlowStatsRequest.Builder;
import org.projectfloodlight.openflow.types.OFGroup;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.TableId;

/**
 * 下发请求报文的两种途径，
 * 1.OFFlowStatusRequest：针对流
 * <p>
 * 2.OFAggregatetatusRequest：针对交换机--交换机统计之后上报所有消息
 * tips：这个更适合我的工程实现
 * --zigzag
 */


public class BandMeter {


    /**
     *
     * 带宽测量
     * @param aSwitch
     * 测量过程:
     * 1.新建并填充请求（status request）消息发起对某个交换机的状态请求
     * 2.NetworkStore（handleFlowStatsReply函数）处理状态请求信息：在返回的FlowStatsReply消息的流表项（匹配域与action中）找到該流对应的出入端口和对应的比特数
     */
    /**
     * OFFlowStatsRequest
     * OFAggregateStatsRequest---某个交换机聚合的消息
     */
    void doBandMeter(IOFSwitch aSwitch) {
        //某个流请求消息
        Builder request = (Builder) aSwitch.getOFFactory().buildFlowStatsRequest();
        //某个交换机的聚合信息
        //Builder r = (Builder) aSwitch.getOFFactory().buildAggregateStatsRequest();

        //TODO 相关配置可能需要更改 --zigzag
        //流表ID
        request.setTableId(TableId.ALL);
        //请求的出端口
        request.setOutPort(OFPort.ANY);
        //ANY表示是任意的Group，不关心
        request.setOutGroup(OFGroup.ANY);
        request.setCookie(AppCookie.makeCookie(2, 0));
        //MyLog.info("带宽测量发起请求-发送？请求");
        aSwitch.write(request.build());

    }
}