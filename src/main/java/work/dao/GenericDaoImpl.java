package work.dao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * {@inheritDoc}
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImpl<T extends Serializable, ID extends Number> extends AbstractDao<T, ID> {

}
