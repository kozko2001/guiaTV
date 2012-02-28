//
//  ProgramacioParser.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 24/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ProgramacioParser.h"
#import "ProgramaModel.h"
#import "ProgramacioModel.h"

@implementation ProgramacioParser

- (ProgramacioModel*)parse: (NSString*) channelId
{
    programas = [[NSMutableArray alloc] initWithCapacity: 10];
    
    NSString *s_url = [NSString stringWithFormat: @"http://localhost:9000/channels/%@" , channelId];
    NSURL *url = [[NSURL alloc] initWithString: s_url];
    
    xmlParser = [[NSXMLParser alloc] initWithContentsOfURL:url];
    [xmlParser setDelegate:self];
    [xmlParser setShouldProcessNamespaces:NO];
    [xmlParser setShouldReportNamespacePrefixes:NO];
    [xmlParser setShouldResolveExternalEntities:NO];
    [xmlParser parse];
    
    NSArray *p =  [programas copy];
    
    ProgramacioModel *model = [[ProgramacioModel alloc] initFromProgramas: p];
    return model;
}


#pragma mark -
#pragma mark NSXMLParserDelegate methods

- (void)parserDidStartDocument:(NSXMLParser *)parser 
{

    
}

- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError 
{
    NSLog(@"Error: %@", [parseError localizedDescription]);
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    currentElementName = elementName;
    if( [elementName isEqualToString: @"programme"])
    {
        currentStart =[[attributeDict objectForKey: @"start"]  doubleValue];
        currentEnd = [[attributeDict objectForKey: @"end"]  doubleValue];

    }
    if( [elementName isEqualToString:@"title"] || [elementName isEqualToString:@"desc"] || [elementName isEqualToString:@"categoria"] || [elementName isEqualToString:@"image"])
    {
        text = [[NSMutableString alloc] init];
    }
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if([elementName isEqualToString: @"programme"])
    {
//        NSLog(@"%@ %@", currentTitle, currentImagen);
        ProgramaModel *programa = [[ProgramaModel alloc] initProgramaWithTitle:currentTitle AndDesc:currentDesc
                                                                AndCategoria:currentCategoria AndStart:currentStart AndEnd:currentEnd AndImage:currentImagen];
        [programas addObject: programa];
    }
    
    if( [elementName isEqualToString:@"title"])
       currentTitle = [text copy];

    if( [elementName isEqualToString:@"categoria"])
        currentCategoria = [text copy];
    
    if( [elementName isEqualToString:@"desc"])
        currentDesc = [text copy];
    
    if( [elementName isEqualToString:@"image"])
        currentImagen = [[text copy] stringByTrimmingCharactersInSet: [NSCharacterSet whitespaceAndNewlineCharacterSet]];
    
}        

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)chars
{
    if( [currentElementName isEqualToString:@"title"] || [currentElementName isEqualToString:@"desc"] ||
        [currentElementName isEqualToString:@"categoria"] || [currentElementName isEqualToString:@"image"] )
    {
        [text appendString:chars];
    } 
}

- (void)parserDidEndDocument:(NSXMLParser *)parser 
{
}

@end
