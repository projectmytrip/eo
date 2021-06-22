package it.reply.compliance.commons.persistence.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

public class TestPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

    public static final String TABLE_NAME_PREFIX = "test_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        Identifier newIdentifier = new Identifier(TABLE_NAME_PREFIX + name, name.isQuoted());
        return super.toPhysicalTableName(newIdentifier, context);
    }
}
