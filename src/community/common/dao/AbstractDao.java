package community.common.dao;

import koonisoft.jas.JasRuntimeProperties;

public class AbstractDao {

   private JasRuntimeProperties runtimeProp = null;
   
   public AbstractDao(JasRuntimeProperties runtimeProp) {
      this.runtimeProp = runtimeProp;
   }
   
   public JasRuntimeProperties getRuntimeProp() {
      return runtimeProp;
   }
}
