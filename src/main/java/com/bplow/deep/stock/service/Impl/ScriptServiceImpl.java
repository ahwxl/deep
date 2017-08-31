/**
 * www.bplow.com
 */
package com.bplow.deep.stock.service.Impl;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ognl.Node;
import ognl.Ognl;
import ognl.OgnlContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bplow.deep.stock.service.ScriptService;

/**
 * @desc 
 * @author wangxiaolei
 * @date 2017年2月25日 下午5:40:56
 */
@Service
public class ScriptServiceImpl implements ScriptService {

    private Logger                          logger   = LoggerFactory.getLogger(this.getClass());

    private ConcurrentHashMap<String, Node> nodesMap = new ConcurrentHashMap<String, Node>();

    private Lock                            lock     = new ReentrantLock(true);

    @Override
    public boolean executeScripte(String script, Map<String, Serializable> parament) {

        boolean result = false;

        try {
            Node node = obtainNode(script);

            result = (Boolean) Ognl.getValue(node, parament);

        } catch (Exception e) {
            logger.error("执行OGNL表达式异常:{}", e);
        }

        return result;
    }

    public Node obtainNode(String script) {
        Node node = null;

        node = nodesMap.get(script);

        if (node == null) {
            lock.lock();
            OgnlContext context = null;
            try {

                logger.info("编译表达式");
                context = (OgnlContext) Ognl.createDefaultContext(null);
                Object root = null;

                node = Ognl.compileExpression(context, root, script);

                nodesMap.put(script, node);

            } catch (Exception e) {
                logger.error("编译ognl脚本失败:{}", e);
            }

            lock.unlock();
        }

        return node;
    }

}
