system.graph('customer360').ifNotExists().create()

// Properties
schema.propertyKey('cluster_id').Text().ifNotExists().create()
schema.propertyKey('cluster_size').Text().ifNotExists().create()
schema.propertyKey('confidence_level').Decimal().ifNotExists().create()
schema.propertyKey('golden_company_name').Text().ifNotExists().create()
schema.propertyKey('golden_display_name').Text().ifNotExists().create()
schema.propertyKey('golden_company_reg_no').Text().ifNotExists().create()
schema.propertyKey('golden_customer_name').Text().ifNotExists().create()
schema.propertyKey('golden_customer_type').Text().ifNotExists().create()
schema.propertyKey('golden_dob').Date().ifNotExists().create()
schema.propertyKey('golden_firstname').Text().ifNotExists().create()
schema.propertyKey('golden_surname').Text().ifNotExists().create()

schema.propertyKey('cd_si_ext').Text().ifNotExists().create()
schema.propertyKey('src_customer_id').Text().ifNotExists().create()
schema.propertyKey('customer_type').Text().ifNotExists().create()
schema.propertyKey('firstname').Text().ifNotExists().create()
schema.propertyKey('surname').Text().ifNotExists().create()
schema.propertyKey('company_reg_no').Text().ifNotExists().create()
schema.propertyKey('company_name').Text().ifNotExists().create()
schema.propertyKey('customer_type').Text().ifNotExists().create()
schema.propertyKey('dob').Date().ifNotExists().create()

schema.propertyKey('agreement_id').Text().ifNotExists().create()
schema.propertyKey('sys_apl').Text().ifNotExists().create()
schema.propertyKey('agreement_start_date').Date().ifNotExists().create()
schema.propertyKey('maturity_date').Date().ifNotExists().create()
schema.propertyKey('terminated_date').Date().ifNotExists().create()
schema.propertyKey('contract_type').Text().ifNotExists().create()
schema.propertyKey('vin_no').Text().ifNotExists().create()
schema.propertyKey('status_id').Text().ifNotExists().create()


// Vertex labels
schema.vertexLabel('cluster')
      .partitionKey('cluster_id')
      .properties('cluster_id', 'cluster_size', 'confidence_level',
                  'golden_company_name',  'golden_display_name',  'golden_company_reg_no',
                  'golden_customer_name', 'golden_customer_type', 'golden_dob', 'golden_firstname', 'golden_surname')
      .ifNotExists().create()
schema.vertexLabel('customer')
      .partitionKey('src_customer_id')
      .properties('src_customer_id', 'cd_si_ext', 'customer_type', 
                  'firstname',  'surname',  'company_reg_no',
                  'company_name', 'customer_type', 'dob')
      .ifNotExists().create()
schema.vertexLabel('contract')
      .partitionKey('agreement_id')
      .properties('agreement_id', 'src_customer_id', 'sys_apl', 
                  'agreement_start_date',  'maturity_date',  'terminated_date',
                  'contract_type', 'vin_no', 'status_id')
      .ifNotExists().create()
schema.vertexLabel('vehicule')
      .partitionKey('vin_no')
      .properties('vin_no')
      .ifNotExists().create()

// Edge labels
schema.edgeLabel('containsCustomer').connection('cluster', 'customer').ifNotExists().create()
schema.edgeLabel('relatedCluster').connection('customer', 'cluster').ifNotExists().create()
schema.edgeLabel('suscriptedBy').connection('contract', 'customer').ifNotExists().create()
schema.edgeLabel('suscriptedFor').connection('contract', 'cluster').ifNotExists().create()
schema.edgeLabel('subjectOfContract').connection('contract', 'vehicule').ifNotExists().create()
schema.edgeLabel('relatedContract').connection('vehicule', 'contract').ifNotExists().create()
