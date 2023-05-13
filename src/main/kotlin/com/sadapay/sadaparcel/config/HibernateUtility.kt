package com.sadapay.sadaparcel.config

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder


class HibernateUtility {
    companion object {
        fun getSessionFactory(): SessionFactory {
            val registry = StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build()
            return MetadataSources(registry)
                .buildMetadata().buildSessionFactory()
        }
    }
}