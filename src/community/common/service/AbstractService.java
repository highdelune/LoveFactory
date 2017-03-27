package community.common.service;

import koonisoft.jas.JasRuntimeProperties;

public class AbstractService {
   private JasRuntimeProperties runtimeProp = null;
   
   public AbstractService(JasRuntimeProperties runtimeProp) {
      this.runtimeProp = runtimeProp;
   }
   
   public JasRuntimeProperties getRuntimeProp() {
      return runtimeProp;
   }

}
